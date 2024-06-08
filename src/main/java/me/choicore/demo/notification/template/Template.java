package me.choicore.demo.notification.template;

public interface Template {
    String render();

    boolean hasPlaceholders();
}
