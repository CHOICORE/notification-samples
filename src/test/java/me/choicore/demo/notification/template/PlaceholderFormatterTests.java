package me.choicore.demo.notification.template;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceholderFormatterTests {

    @Test
    void t1() {
        PlaceholderFormatter formatter = new DefaultPlaceholderFormatter();
        String prefix = formatter.prefix();
        String suffix = formatter.suffix();
        String target = "target";
        assertThat(formatter.format(target)).isEqualTo(prefix + target + suffix);
    }
}