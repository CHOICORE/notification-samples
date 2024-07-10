package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTemplateBuilderTests {
    @Test
    void t1() {
        ContentTemplate template = ContentTemplate.builder()
                .name("welcome")
                .content("Hello, {name}! Welcome to {company}!")
                .build();
        assertThat(template.hasPlaceholders()).isTrue();
    }

    @Test
    void t2() {
        String templateName = "welcome";
        PlaceholderFormatter placeholderFormatter = new DefaultPlaceholderFormatter("#{", "}");

        ContentTemplate template = ContentTemplate.builder()
                .placeholderDefinition(new PlaceholderDefinition(placeholderFormatter))
                .name(templateName)
                .content("Hello, #{name}! Welcome to #{company}!")
                .build();
        assertThat(template.hasPlaceholders()).isTrue();
    }

    @Test
    void t3() {
        assertThatThrownBy(() -> ContentTemplate.builder()
                .placeholderDefinition(new PlaceholderDefinition("#{", "}"))
                .name("welcome")

                .content("Hello, {name}! Welcome to #{company}!")
                .build()
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid placeholder");
    }

    @Test
    void t4() {
        String templateName = "welcome";
        ContentTemplate template = ContentTemplate.builder()
                .placeholderDefinition(PlaceholderDefinitions.create("#{", "}"))
                .name(templateName)
                .content("Hello, #{name}! Welcome to #{company}!")
                .build();
        assertThat(template.hasPlaceholders()).isTrue();
    }

    @Test
    void t5() {
        assertThatThrownBy(() -> ContentTemplate.builder()
                .placeholderDefinition(PlaceholderDefinitions.create("#{", "}"))
                .name("welcome")
                .content("Hello, {name}! Welcome to {company}!")
                .build()
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid placeholder");
    }

    @Test
    void t6() throws Exception {
        Resource resource = new ClassPathResource("/templates/notification/welcome-template.html");
        ContentTemplate template = ContentTemplate.builder()
                .placeholderDefinition(PlaceholderDefinitions.create("#{", "}"))
                .name("welcome")
                .content(resource.getFile())
                .build();
        assertThat(template.hasPlaceholders()).isTrue();
    }
}