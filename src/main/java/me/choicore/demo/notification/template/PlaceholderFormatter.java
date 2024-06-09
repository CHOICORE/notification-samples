package me.choicore.demo.notification.template;

import java.util.Set;
import java.util.regex.Pattern;

public interface PlaceholderFormatter {
    String DEFAULT_PLACEHOLDER_PREFIX = "{";
    String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    default String format(String key) {
        return getPrefix() + key + getSuffix();
    }

    Set<String> extractPlaceholders(String template);

    Pattern getPlaceholderPattern();

    boolean hasPlaceholders(String template);
}
