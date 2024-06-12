package me.choicore.demo.notification.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TemplateRenderer {
    public Template render(Template template, PlaceholderRegistry registry) {
        if (!template.hasPlaceholders()) {
            return template;
        }

        // TODO: 리플레이싱 해야하는 작업 유형이 복잡해지면 리졸버를 통해 리플레이싱 처리하도록 수정
        if (template instanceof SubjectContentTemplate subjectContentTemplate) {
            String subject = replacePlaceholders(subjectContentTemplate.getSubject(), registry);
            String content = replacePlaceholders(subjectContentTemplate.getContent(), registry);
            return SubjectContentTemplate.builder()
                    .placeholderDefinition(registry.getPlaceholderDefinition())
                    .name(subjectContentTemplate.getName())
                    .subject(subject)
                    .content(content)
                    .build();
        } else {
            String content = replacePlaceholders(template.getContent(), registry);
            return ContentTemplate.builder()
                    .placeholderDefinition(registry.getPlaceholderDefinition())
                    .name(template.getName())
                    .content(content)
                    .build();
        }
    }

    private String replacePlaceholders(String source, PlaceholderRegistry registry) {
        for (Placeholder placeholder : registry.getPlaceholders()) {
            source = source.replace(placeholder.target(), placeholder.replacement());
        }
        checkForUnresolvedPlaceholders(source, registry);
        return source;
    }

    private void checkForUnresolvedPlaceholders(String template, PlaceholderRegistry registry) {
        Set<String> unresolvedPlaceholders = getRequiredReplacingPlaceholders(template, registry);
        if (!unresolvedPlaceholders.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(
                            "The template contains unresolved placeholders: %s. " +
                                    "Please ensure all placeholders are registered in the PlaceholderRegistry.",
                            unresolvedPlaceholders
                    )
            );
        }
    }

    private Set<String> getRequiredReplacingPlaceholders(String template, PlaceholderRegistry registry) {
        return registry.getPlaceholderFormatter().extractPlaceholders(template);
    }
}
