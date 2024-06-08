package me.choicore.demo.notification.template;

public record Placeholder(
        String target,
        String replacement
) {
    public Placeholder {
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException("Target must not be null or blank");
        }
        if (replacement == null) {
            throw new IllegalArgumentException("Replacement must not be null");
        }
    }
}
