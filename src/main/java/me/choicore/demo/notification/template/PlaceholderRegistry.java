package me.choicore.demo.notification.template;

import java.util.List;

public interface PlaceholderRegistry {
    void registerPlaceholder(Placeholder placeholder);

    String getReplacement(String placeholder);

    boolean isRegistered(String placeholder);

    List<Placeholder> getPlaceholders();
}
