package me.choicore.demo.notification.template;

import jakarta.annotation.Nonnull;

public class ContentTemplate implements Template {
    private final String name;
    private final String content;
    private final TemplateType type;
    private final boolean hasPlaceholders;

    public ContentTemplate(
            @Nonnull final String name,
            @Nonnull final String content,
            @Nonnull final TemplateType type,
            final boolean hasPlaceholders) {
        this.name = name;
        this.content = content;
        this.type = type;
        this.hasPlaceholders = hasPlaceholders;
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
    public TemplateType getType() {
        return this.type;
    }

    @Override
    public boolean hasPlaceholders() {
        return this.hasPlaceholders;
    }
}
