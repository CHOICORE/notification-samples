package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceholderRegistryTests {
    @Test
    void t1() {
        PlaceholderFormatter formatter = new DefaultPlaceholderFormatter();
        PlaceholderRegistry registry = new DefaultPlaceholderRegistry(formatter);

        registry.registerPlaceholder(Placeholders.personName("홍길동"));
        registry.registerPlaceholder(Placeholders.companyName("개발바닥"));
        registry.registerPlaceholder(Placeholders.as("link", "https://company.com"));

        for (Placeholder placeholder : registry.getPlaceholders()) {
            assertThat(placeholder.target()).startsWith(formatter.prefix()).endsWith(formatter.suffix());
            assertThat(placeholder.replacement()).isNotNull();
        }
    }
}
