package me.choicore.demo.notification;

import lombok.Getter;

@Getter
public class PlaceholderExpression {
    private final String placeholder;
    private final String replacement;

    public PlaceholderExpression(String placeholder, String replacement) {
        this.placeholder = placeholder;
        this.replacement = replacement;
    }
}
