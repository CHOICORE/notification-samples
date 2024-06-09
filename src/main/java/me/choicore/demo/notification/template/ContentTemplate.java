package me.choicore.demo.notification.template;

import jakarta.annotation.Nonnull;

public class ContentTemplate implements Template {
    private final String name;
    private final String content;
    private final boolean hasPlaceholders;

    protected ContentTemplate(
            @Nonnull final String name,
            @Nonnull final String content,
            final boolean hasPlaceholders) {
        this.name = name;
        this.content = content;
        this.hasPlaceholders = hasPlaceholders;
    }

    public static ContentTemplateBuilder builder() {
        return new ContentTemplateBuilder();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public boolean hasPlaceholders() {
        return this.hasPlaceholders;
    }

    public static class ContentTemplateBuilder {
        protected String name;
        protected String content;
        protected PlaceholderDefinition definition = PlaceholderDefinitions.withDefault();

        public ContentTemplateBuilder placeholderDefinition(PlaceholderDefinition definition) {
            if (definition == null) throw new IllegalArgumentException("PlaceholderDefinition must not be null");
            this.definition = definition;
            return this;
        }

        public ContentTemplateBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ContentTemplateBuilder content(String content) {
            this.content = content;
            return this;
        }


        public ContentTemplate build() {
            return new ContentTemplate(name, content, getValidator().hasPlaceholders(content));
        }

        protected PlaceholderValidator getValidator() {
            return new DefaultPlaceholderValidator(definition);
        }
    }
}
