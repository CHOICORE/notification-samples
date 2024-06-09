package me.choicore.demo.notification.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SubjectContentTemplateBuilderTests {
    @Test
    void t1() {
        SubjectContentTemplate template = SubjectContentTemplate.builder()
                .subject("Welcome to {company}!")
                .name("welcome")
                .content("Hello, {name}! Welcome to {company}!")
                .build();
        Assertions.assertThat(template.hasPlaceholders()).isTrue();
    }

    @Test
    void t2() {
        String templateName = "welcome";
        PlaceholderFormatter placeholderFormatter = new DefaultPlaceholderFormatter();
        placeholderFormatter.setPrefix("#{");
        placeholderFormatter.setSuffix("}");
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
        Assertions.assertThatThrownBy(() -> SubjectContentTemplate.builder()
                        .placeholderDefinition(new PlaceholderDefinition("#{", "}"))
                        .name("welcome")
                        .subject("Welcome to #{company}!")
                        .content("Hello, {name}! Welcome to #{company}!")
                        .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid placeholder");
    }

    @Test
    void t4() {
        String templateName = "welcome";
        SubjectContentTemplate template = SubjectContentTemplate.builder()
                .placeholderDefinition(PlaceholderDefinitions.create("#{", "}"))
                .name(templateName)
                .subject("Welcome to #{company}!")
                .content("Hello, #{name}! Welcome to #{company}!")
                .build();
        Assertions.assertThat(template.hasPlaceholders()).isTrue();
    }

    @Test
    void t5() {
        Assertions.assertThatThrownBy(() -> SubjectContentTemplate.builder()
                        .placeholderDefinition(PlaceholderDefinitions.create("#{", "}"))
                        .name("welcome")
                        .subject("Hi {name} Welcome to {company}!")
                        .content("Hello, {name}! Welcome to {company}!")
                        .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid placeholder");
    }
}