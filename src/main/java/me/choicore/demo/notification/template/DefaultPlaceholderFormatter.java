package me.choicore.demo.notification.template;

import java.util.Objects;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultPlaceholderFormatter implements PlaceholderFormatter, PlaceholderValidator {
    private String prefix = DEFAULT_PLACEHOLDER_PREFIX;
    private String suffix = DEFAULT_PLACEHOLDER_SUFFIX;

    public DefaultPlaceholderFormatter() {
    }

    public DefaultPlaceholderFormatter(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
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

    @Override
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

    @Override
    public Pattern getPlaceholderPattern() {
        return Pattern.compile(Pattern.quote(prefix) + "(.*?)" + Pattern.quote(suffix));
    }

    @Override
    public boolean hasPlaceholders(String template) {
        return getPlaceholderPattern().matcher(template).find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultPlaceholderFormatter formatter = (DefaultPlaceholderFormatter) o;
        return Objects.equals(getPrefix(), formatter.getPrefix()) && Objects.equals(getSuffix(), formatter.getSuffix());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrefix(), getSuffix());
    }
}
