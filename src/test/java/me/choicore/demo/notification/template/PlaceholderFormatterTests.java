package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceholderFormatterTests {

    @Test
    void t1() {
        PlaceholderFormatter formatter = new DefaultPlaceholderFormatter();
        String prefix = formatter.getPrefix();
        String suffix = formatter.getSuffix();
        String target = "target";
        assertThat(formatter.format(target)).isEqualTo(prefix + target + suffix);
    }

    @Test
    void t2() {
        PlaceholderFormatter formatter = new DefaultPlaceholderFormatter();
        String template = "Hello, {name}!";
        assertThat(formatter.extractPlaceholders(template)).containsExactly("{name}");
    }

    @Test
    void t3() {
        PlaceholderFormatter formatter = new DefaultPlaceholderFormatter();
        formatter.setPrefix("${");
        formatter.setSuffix("}");
        String template = "Hello, ${name}!";
        assertThat(formatter.extractPlaceholders(template)).containsExactly("${name}");
    }
}