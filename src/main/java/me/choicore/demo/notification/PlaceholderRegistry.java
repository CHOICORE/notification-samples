package me.choicore.demo.notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceholderRegistry {
    private final List<PlaceholderExpression> registry = new ArrayList<>();
    private PlaceholderFormat format;

    public PlaceholderRegistry() {
        this.format = PlaceholderFormat.withDefault();
    }

    public void register(PlaceholderExpression... expression) {
        registry.addAll(Arrays.asList(expression));
    }

    public String replace(String template) {
        for (PlaceholderExpression expression : registry) {
            template = template.replace(makePlaceholderName(expression.getPlaceholder()), expression.getReplacement());
        }
        return template;
    }

    public void setFormat(PlaceholderFormat format) {
        if (format == null) {
            throw new IllegalArgumentException("format cannot be null");
        }
        this.format = format;
    }

    private String makePlaceholderName(String placeholder) {
        return format.prefix() + placeholder + format.suffix();
    }
}
