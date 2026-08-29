package com.synechisveltiosi.apis.app365.apache.camel;

import com.synechisveltiosi.apis.app365.accounts.config.SocialTokenConfig;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import com.synechisveltiosi.apis.app365.candidates.service.CandidateService;
import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class CamelRouterBuilderService extends RouteBuilder {

    private final static Logger logger = LoggerFactory.getLogger(CamelRouterBuilderService.class);

    private final CandidateService candidateService;
    private final Long facebookPostPollingDelay;
    private final Long twitterPostPollingDelay;
    private Candidate candidate = null;

    @Autowired
    public CamelRouterBuilderService(
            CandidateService candidateService,
            @Value("${spring.social.facebook.poll-delay}") Long facebookPostPollingDelay,
            @Value("${spring.social.twitter.poll-delay}") Long twitterPostPollingDelay) {

        super();

        this.candidateService = candidateService;
        this.facebookPostPollingDelay = facebookPostPollingDelay * 1000;
        this.twitterPostPollingDelay = twitterPostPollingDelay * 1000;
    }

    @Override
    public void configure() throws Exception {
        // Initialize candidate if null
        if (candidate == null) candidate = candidateService.findFirstCandidate().orElse(null);

        // If no candidate, return
        if (candidate == null || candidate.getSocialTokens() == null) return;

        // Setup facebook routes
        configureFacebookRoutes();

        // Setup twitter routes
        configureTwitterRoutes();
    }

    private void configureFacebookRoutes() {
        SocialTokenConfig fbToken = null;
        for (SocialTokenConfig token : candidate.getSocialTokens()) {
            if (token.getProvider() == SocialNetworkProvider.FACEBOOK) {
                fbToken = token;
                break;
            }
        }

        // If no token, return
        if (fbToken == null || !fbToken.isValid()) return;

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("consumer.delay", String.valueOf(facebookPostPollingDelay)));
        params.add(new BasicNameValuePair("oAuthAppId", fbToken.getAppId()));
        params.add(new BasicNameValuePair("oAuthAppSecret", fbToken.getAppSecret()));
        params.add(new BasicNameValuePair("oAuthAccessToken", fbToken.getAccessToken()));

        from("facebook://feed?" + URLEncodedUtils.format(params, "UTF-8"))
                .streamCaching()
                .process(new FacebookPostProcessor());
    }

    private void configureTwitterRoutes() {
        SocialTokenConfig twToken = null;
        for (SocialTokenConfig token : candidate.getSocialTokens()) {
            if (token.getProvider() == SocialNetworkProvider.TWITTER) {
                twToken = token;
                break;
            }
        }

        // If no token, return
        if (twToken == null || !twToken.isValid()) return;

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("type", "polling"));
        params.add(new BasicNameValuePair("delay", String.valueOf(twitterPostPollingDelay)));
        params.add(new BasicNameValuePair("user", twToken.getUserId()));
        params.add(new BasicNameValuePair("consumerKey", twToken.getAppId()));
        params.add(new BasicNameValuePair("consumerSecret", twToken.getAppSecret()));
        params.add(new BasicNameValuePair("accessToken", twToken.getAccessToken()));
        params.add(new BasicNameValuePair("accessTokenSecret", twToken.getAccessTokenSecret()));

        from("twitter://timeline/user?" + URLEncodedUtils.format(params, "UTF-8"))
                .streamCaching()
                .process(new TwitterPostProcessor());
    }

    private class FacebookPostProcessor implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            // Deserialize post
            facebook4j.Post fbPost = exchange.getIn().getBody(facebook4j.Post.class);

            // Initialize candidate if null
            if (candidate == null) candidate = candidateService.findFirstCandidate().orElse(null);

            // If no candidate, return
            if (candidate == null) return;

            // Create local post
            Post post = new Post();
            post.setExternalPostId(fbPost.getId());
            post.setChannel(SocialNetworkProvider.FACEBOOK);
            post.setExternalCreatedAt(LocalDateTime.ofInstant(fbPost.getCreatedTime().toInstant(),
                    ZoneId.systemDefault()));
            post.setContent(fbPost.getMessage());

            // Log post content
            logger.debug(post.getContent());

            // Save the candidate post
            candidateService.savePost(candidate.getCandidateId(), post);
        }
    }

    private class TwitterPostProcessor implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            // Deserialize post
            twitter4j.Status twPost = exchange.getIn().getBody(twitter4j.Status.class);

            // Initialize candidate if null
            if (candidate == null) candidate = candidateService.findFirstCandidate().orElse(null);

            // If no candidate, return
            if (candidate == null) return;

            // Create local post
            Post post = new Post();
            post.setExternalPostId(String.valueOf(twPost.getId()));
            post.setChannel(SocialNetworkProvider.TWITTER);
            post.setExternalCreatedAt(LocalDateTime.ofInstant(twPost.getCreatedAt().toInstant(),
                    ZoneId.systemDefault()));
            post.setContent(twPost.getText());

            // Log post content
            logger.debug(post.getContent());

            try {
                // Save the candidate post
                candidateService.savePost(candidate.getCandidateId(), post);
            } catch (DataIntegrityViolationException ex) {
                logger.warn("Twitter duplicated tweets report: " + ex.getMessage());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
