package me.choicore.demo.notification.push;

import me.choicore.demo.notification.AbstractTemplate;

public class PushTemplate extends AbstractTemplate {
    String message;

    private PushTemplate(String message) {
        super(message);
    }

}
