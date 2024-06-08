package me.choicore.demo.notification.template;

public interface Template {
    String getName();

    String getContent();

    TemplateType getType();

    boolean hasPlaceholders();
}
