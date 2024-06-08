package me.choicore.demo.notification.template;

import java.util.Set;

public interface PlaceholderFormatter {
    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    default String format(String key) {
        return getPrefix() + key + getSuffix();
    }

    Set<String> extractPlaceholders(String template);
}
