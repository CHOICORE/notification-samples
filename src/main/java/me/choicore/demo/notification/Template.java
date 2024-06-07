package me.choicore.demo.notification;

import java.util.Set;

public interface Template {

    boolean hasPlaceholders();

    int getPlaceholderCount();


    String getTemplate();

    Set<String> getPlaceholders();
}
