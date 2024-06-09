package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceholderRegistryTests {
    @Test
    void t1() {
        PlaceholderDefinition definition = PlaceholderDefinitions.withDefault();
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(definition);

        registry.registerPlaceholder(Placeholders.personName("홍길동"));
        registry.registerPlaceholder(Placeholders.companyName("개발바닥"));
        registry.registerPlaceholder(Placeholders.as("link", "https://company.com"));

        for (Placeholder placeholder : registry.getPlaceholders()) {
            assertThat(placeholder.target()).startsWith(definition.getStartWith()).endsWith(definition.getEndWith());
            assertThat(placeholder.replacement()).isNotNull();
        }
    }

    @Test
    void t2() {
        PlaceholderDefinition definition = PlaceholderDefinitions.withDefault();
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(definition);

        registry.registerPlaceholder(Placeholders.personName("홍길동"));
        registry.registerPlaceholder(Placeholders.companyName("개발바닥"));
        registry.registerPlaceholder(Placeholders.as("link", "https://company.com"));

        assertThat(registry.getReplacement(Placeholders.PERSON_NAME)).isEqualTo("홍길동");
        assertThat(registry.getReplacement(Placeholders.COMPANY_NAME)).isEqualTo("개발바닥");
        assertThat(registry.getReplacement("link")).isEqualTo("https://company.com");
    }
}
