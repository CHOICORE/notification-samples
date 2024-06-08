package me.choicore.demo.notification.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPlaceholderRegistry implements PlaceholderRegistry {
    private final Map<String, String> placeholders = new HashMap<>();
    private PlaceholderFormatter formatter;

    public DefaultPlaceholderRegistry(PlaceholderFormatter formatter) {
        this.formatter = formatter;
    }

    public DefaultPlaceholderRegistry() {
        this(new DefaultPlaceholderFormatter());
    }

    public void setPlaceholderFormatter(PlaceholderFormatter formatter) {
        if (formatter == null) {
            throw new IllegalArgumentException("formatter cannot be null");
        }
        this.formatter = formatter;
    }

    private void registerPlaceholder(String placeholder, String replacement) {
        placeholders.put(determinePlaceholderName(placeholder), replacement);
    }

    @Override
    public void registerPlaceholder(Placeholder placeholder) {
        registerPlaceholder(placeholder.target(), placeholder.replacement());
    }

    @Override
    public String getReplacement(String placeholder) {
        return placeholders.get(determinePlaceholderName(placeholder));
    }

    @Override
    public boolean isRegistered(String placeholder) {
        return placeholders.containsKey(determinePlaceholderName(placeholder));
    }

    @Override
    public List<Placeholder> getPlaceholders() {
        return placeholders
                .entrySet()
                .stream()
                .map(entry -> new Placeholder(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public PlaceholderFormatter getPlaceholderFormatter() {
        return this.formatter;
    }

    private String determinePlaceholderName(String placeholder) {
        return formatter.format(placeholder);
    }
}
