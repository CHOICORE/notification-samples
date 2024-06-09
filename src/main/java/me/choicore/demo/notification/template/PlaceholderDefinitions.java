package me.choicore.demo.notification.template;

public class PlaceholderDefinitions {

    public static PlaceholderDefinition withDefault() {
        return new PlaceholderDefinition(new DefaultPlaceholderFormatter());
    }

    public static PlaceholderDefinition create(String startWith, String endWith) {
        return new PlaceholderDefinition(startWith, endWith);
    }
}
