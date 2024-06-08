package me.choicore.demo.notification.template;

import java.util.Set;

public class PlaceholderReplacingTemplate implements Template {
    private final String template;
    private final PlaceholderRegistry registry;

    public PlaceholderReplacingTemplate(String template, PlaceholderRegistry registry) {
        if (template == null || template.isBlank()) {
            throw new IllegalArgumentException("Template must not be null or blank");
        }

        if (registry == null) {
            throw new IllegalArgumentException("Registry must not be null");
        }

        this.template = template;
        this.registry = registry;
    }

    @Override
    public String render() {
        String result = template;
        for (Placeholder placeholder : registry.getPlaceholders()) {
            result = result.replace(placeholder.target(), placeholder.replacement());
        }

        checkForUnresolvedPlaceholders(result);
        return result;
    }

    @Override
    public boolean hasPlaceholders() {
        return !getRequiredReplacingPlaceholders(template).isEmpty();
    }

    private void checkForUnresolvedPlaceholders(String template) {
        Set<String> unresolvedPlaceholders = getRequiredReplacingPlaceholders(template);
        if (!unresolvedPlaceholders.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                    "The template contains unresolved placeholders: %s. " +
                            "Please ensure all placeholders are registered in the PlaceholderRegistry.", unresolvedPlaceholders));
        }
    }

    private Set<String> getRequiredReplacingPlaceholders(String template) {
        return registry.getPlaceholderFormatter().extractPlaceholders(template);
    }
}
