package me.choicore.demo.notification.template;

import java.util.Set;

public class PloaceholderReplacingRenderer implements Renderer {
    private final Template template;
    private final PlaceholderRegistry registry;

    public PloaceholderReplacingRenderer(Template template, PlaceholderRegistry registry) {
        if (template == null) {
            throw new IllegalArgumentException("Template must not be null");
        }

        if (registry == null) {
            throw new IllegalArgumentException("Registry must not be null");
        }

        this.template = template;
        this.registry = registry;
    }

    @Override
    public Template render() {
        if (!template.hasPlaceholders()) {
            return template;
        }

        // TODO: 리플레이싱 해야하는 작업 유형이 복잡해지면 리졸버를 통해 리플레이싱 처리하도록 수정
        if (template instanceof SubjectContentTemplate subjectContentTemplate) {
            String subject = replacePlaceholders(subjectContentTemplate.getSubject());
            String content = replacePlaceholders(subjectContentTemplate.getContent());
            return new SubjectContentTemplate(subjectContentTemplate.getName(), subject, content, false);
        } else {
            String content = replacePlaceholders(template.getContent());
            return new ContentTemplate(template.getName(), content, template.getType(), false);
        }
    }

    private String replacePlaceholders(String source) {
        for (Placeholder placeholder : registry.getPlaceholders()) {
            source = source.replace(placeholder.target(), placeholder.replacement());
        }
        checkForUnresolvedPlaceholders(source);
        return source;
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
