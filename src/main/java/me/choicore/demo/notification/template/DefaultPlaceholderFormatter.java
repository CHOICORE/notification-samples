package me.choicore.demo.notification.template;

import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultPlaceholderFormatter implements PlaceholderFormatter {
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "{";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
    private String prefix = DEFAULT_PLACEHOLDER_PREFIX;
    private String suffix = DEFAULT_PLACEHOLDER_SUFFIX;

    @Override
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null || prefix.isBlank()) {
            throw new IllegalArgumentException("prefix cannot be null");
        }
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        if (suffix == null || suffix.isBlank()) {
            throw new IllegalArgumentException("suffix cannot be null");
        }
        this.suffix = suffix;
    }

    @Override
    public Set<String> extractPlaceholders(String template) {
        return getPlaceholderPattern().matcher(template).results().map(MatchResult::group).collect(Collectors.toSet());
    }

    private Pattern getPlaceholderPattern() {
        return Pattern.compile(Pattern.quote(prefix) + "(.*?)" + Pattern.quote(suffix));
    }
}
