package me.choicore.demo.notification;

import java.util.regex.Pattern;

public interface PlaceholderFormat {

    static PlaceholderFormat withDefault() {
        return new PlaceholderFormat() {
            @Override
            public String prefix() {
                return "{";
            }

            @Override
            public String suffix() {
                return "}";
            }

            @Override
            public Pattern toPattern() {
                return Pattern.compile("\\%s[^}]*\\%s".formatted(prefix(), suffix()));
            }
        };
    }

    String prefix();

    String suffix();

    Pattern toPattern();
}
