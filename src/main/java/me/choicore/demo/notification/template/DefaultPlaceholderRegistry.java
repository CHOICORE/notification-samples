package me.choicore.demo.notification.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPlaceholderRegistry implements PlaceholderRegistry {
    private final Map<String, String> placeholders = new HashMap<>();
    private final PlaceholderDefinition definition;


    public DefaultPlaceholderRegistry(PlaceholderDefinition definition) {
        this.definition = definition;
    }

    public DefaultPlaceholderRegistry() {
        this(PlaceholderDefinitions.withDefault());
    }

    private void registerPlaceholder(String placeholder, String replacement) {
        placeholders.put(determinePlaceholderName(placeholder), replacement);
    }

    @Override
    public void registerPlaceholder(Placeholder placeholder) {
        registerPlaceholder(placeholder.target(), placeholder.replacement());
    }

    @Override
    public void registerPlaceholders(Placeholder... placeholders) {
        if (placeholders == null) {
            throw new IllegalArgumentException("placeholders cannot be null");
        }

        for (Placeholder placeholder : placeholders) {
            registerPlaceholder(placeholder);
        }
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
        return definition.getFormatter();
    }

    @Override
    public PlaceholderDefinition getPlaceholderDefinition() {
        return this.definition;
    }

    private String determinePlaceholderName(String placeholder) {
        return definition.getFormatter().format(placeholder);
    }
}
