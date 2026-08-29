package com.synechisveltiosi.apis.app365.common.util.template.engine.thymeleaf;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import it.ozimov.springboot.mail.service.TemplateService;
import it.ozimov.springboot.mail.service.exception.TemplateException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static com.google.common.io.Files.getFileExtension;

@Service
public class ThymeleafTemplateService implements TemplateService {

    private final SpringTemplateEngine thymeleafEngine;
    private final String thymeleafSuffix;

    @Autowired
    public ThymeleafTemplateService(SpringTemplateEngine thymeleafEngine,
                                    @Value("${spring.thymeleaf.suffix:.html}") String thymeleafSuffix) {

        this.thymeleafEngine = thymeleafEngine;
        this.thymeleafSuffix = thymeleafSuffix;
    }

    @Override
    public
    @NonNull
    String mergeTemplateIntoString(String templateReference, Map<String, Object> model)
            throws IOException, TemplateException {

        final String trimmedTemplateReference = templateReference.trim();
        Preconditions.checkArgument(!Strings.isNullOrEmpty(trimmedTemplateReference),
                "The given template is null, empty or blank");

        if (trimmedTemplateReference.contains(".")) {
            Preconditions.checkArgument(Objects.equals(getNormalizedFileExtension(trimmedTemplateReference),
                            expectedTemplateExtension()),
                    "Expected a Thymeleaf template file with extension '%s', while '%s' was given. " +
                            "To check the default extension look at 'spring.thymeleaf.suffix' in your " +
                            "application.properties file",
                    expectedTemplateExtension(), getNormalizedFileExtension(trimmedTemplateReference));
        }

        final Context context = new Context();
        context.setVariables(model);

        return thymeleafEngine.process(FilenameUtils.removeExtension(trimmedTemplateReference), context);
    }

    @Override
    public String expectedTemplateExtension() {
        return thymeleafSuffix;
    }

    private String getNormalizedFileExtension(final String templateReference) {
        return "." + getFileExtension(templateReference);
    }
}
