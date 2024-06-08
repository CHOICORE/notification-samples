package me.choicore.demo.notification.template;

public interface PlaceholderFormatter {
    String prefix();

    String suffix();

    default String format(String key) {
        return prefix() + key + suffix();
    }
}
