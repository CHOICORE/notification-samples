package me.choicore.demo.notification.template;

import lombok.Getter;

@Getter
public class PlaceholderDefinition {
    private final PlaceholderFormatter formatter;
    private final String startWith;
    private final String endWith;

    public PlaceholderDefinition(PlaceholderFormatter formatter) {
        this.formatter = formatter;
        this.startWith = formatter.getPrefix();
        this.endWith = formatter.getSuffix();
    }

    public PlaceholderDefinition(String startWith, String endWith) {
        this.formatter = new DefaultPlaceholderFormatter(startWith, endWith);
        this.startWith = startWith;
        this.endWith = endWith;
    }
}
