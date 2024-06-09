package me.choicore.demo.notification.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TemplateRegistryTests {
    @Test
    void t1() {
        TemplateRegistry registry = new SimpleTemplateRegistry(new TemplateRepositoryImpl());

        SubjectContentTemplate template = SubjectContentTemplate.builder()
                .subject("Welcome to {company}!")
                .name("welcome")
                .content("Hello, {name}! Welcome to {company}!")
                .build();

        Assertions.assertThat(template.hasPlaceholders()).isTrue();
        Long savedId = registry.registerTemplate(template);
        Assertions.assertThat(savedId).isNotNull();
    }

    @Test
    void t2() {
        String templateName = "welcome";
        PlaceholderFormatter placeholderFormatter = new DefaultPlaceholderFormatter("#{", "}");

        SubjectContentTemplate template = SubjectContentTemplate.builder()
                .placeholderDefinition(new PlaceholderDefinition(placeholderFormatter))
                .name(templateName)
                .subject("Welcome to #{company}!")
                .content("Hello, #{name}! Welcome to #{company}!")
                .build();

        Assertions.assertThat(template.hasPlaceholders()).isTrue();
    }

    @Test
    void t3() {
        String templateName = "welcome";
        SubjectContentTemplate template = SubjectContentTemplate.builder()
                .placeholderDefinition(PlaceholderDefinitions.create("#{", "}"))
                .name(templateName)
                .subject("Welcome to #{company}!")
                .content("Hello, #{name}! Welcome to #{company}!")
                .build();

        Assertions.assertThat(template.hasPlaceholders()).isTrue();
    }
}
