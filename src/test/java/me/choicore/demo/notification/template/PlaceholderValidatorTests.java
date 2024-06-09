package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceholderValidatorTests {
    @Test
    void t1() {
        PlaceholderValidator validator = new DefaultPlaceholderValidator();
        assertThat(validator.hasPlaceholders("Hello, {name}!")).isTrue();
    }

    @Test
    void t2() {
        PlaceholderValidator validator = new DefaultPlaceholderValidator();
        assertThat(validator.hasPlaceholders("Hello, World!")).isFalse();
    }
}