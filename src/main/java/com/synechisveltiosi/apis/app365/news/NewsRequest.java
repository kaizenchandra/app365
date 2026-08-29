package com.synechisveltiosi.apis.app365.news;

import com.synechisveltiosi.apis.app365.news.entity.News;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/2/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsRequest {

    @NotBlank(message = "News title is required")
    @Size(min = 6, max = 100, message = "News title should be 6 to 100 characters. ")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "News content is required")
    @JsonProperty("content")
    private String content;

    @JsonProperty("coverPicture")
    private String coverPicture;

    @JsonProperty("flagged")
    private boolean flagged;

    public News mapToNews() {

        News news = new News();

        news.setTitle(this.getTitle());
        news.setContent(this.getContent());
        news.setCoverPicture(String.format("https://via.placeholder.com/500x500?text=%s", this.getTitle()));
        news.setFlagged(this.isFlagged());

        return news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
}
