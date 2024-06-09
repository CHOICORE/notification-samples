package me.choicore.demo.notification.template;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SubjectContentTemplate extends ContentTemplate {
    private final String subject;

    // TODO: 제목이 필요 없는 템플릿 유형인 경우에 대한 처리가 필요함
    protected SubjectContentTemplate(
            @Nonnull final String name,
            @Nonnull final String subject,
            @Nonnull final String content,
            final boolean hasPlaceholders
    ) {
        super(name, content, hasPlaceholders);
        this.subject = subject;
    }

    public static SubjectContentTemplateBuilder builder() {
        return new SubjectContentTemplateBuilder();
    }

    public static class SubjectContentTemplateBuilder extends ContentTemplate.ContentTemplateBuilder {
        private String subject;

        public SubjectContentTemplateBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        @Override
        public SubjectContentTemplateBuilder placeholderDefinition(PlaceholderDefinition definition) {
            return (SubjectContentTemplateBuilder) super.placeholderDefinition(definition);
        }

        @Override
        public SubjectContentTemplateBuilder name(String name) {
            return (SubjectContentTemplateBuilder) super.name(name);
        }

        @Override
        public SubjectContentTemplateBuilder content(String content) {
            return (SubjectContentTemplateBuilder) super.content(content);
        }

        @Override
        public SubjectContentTemplate build() {
            boolean hasPlaceholdersInSubject = getValidator().hasPlaceholders(subject);
            boolean hasPlaceholdersInContent = getValidator().hasPlaceholders(content);
            return new SubjectContentTemplate(name, subject, content, hasPlaceholdersInSubject || hasPlaceholdersInContent);
        }
    }
}
