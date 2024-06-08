package me.choicore.demo.notification.template;

public class DefaultPlaceholderFormatter implements PlaceholderFormatter {
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "{";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    @Override
    public String prefix() {
        return DEFAULT_PLACEHOLDER_PREFIX;
    }

    @Override
    public String suffix() {
        return DEFAULT_PLACEHOLDER_SUFFIX;
    }
}
