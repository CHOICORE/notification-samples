package me.choicore.demo.notification;

import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public abstract class AbstractTemplate implements Template {
    private final Pattern placeHolderPattern;

    private String template;

    public AbstractTemplate(String template) {
        this.template = template;
        this.placeHolderPattern = PlaceholderFormat.withDefault().toPattern();
    }

    protected AbstractTemplate(PlaceholderFormat placeHolderFormat) {
        this.placeHolderPattern = placeHolderFormat.toPattern();
    }

    @Override
    public boolean hasPlaceholders() {
        return getPlaceholderCount() > 0;
    }

    @Override
    public int getPlaceholderCount() {
        return (int) placeHolderPattern.matcher(template).results().count();
    }

    public Set<String> getPlaceholders() {
        return placeHolderPattern.matcher(template)
                .results()
                .map(MatchResult::group)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public String getTemplate() {
        return template;
    }
}
