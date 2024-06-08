package me.choicore.demo.notification.template;

import jakarta.annotation.Nonnull;
import lombok.Getter;

@Getter
public class SubjectContentTemplate extends ContentTemplate {
    private final String subject;

    public SubjectContentTemplate(
            @Nonnull final String name,
            @Nonnull final String subject,
            @Nonnull final String content,

            final boolean hasPlaceholders
    ) {
        super(name, content, TemplateType.EMAIL, hasPlaceholders);
        this.subject = subject;
    }
}
