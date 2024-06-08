package me.choicore.demo.notification.template;

public class Placeholders {
    public static final String PERSON_NAME = "personName";
    public static final String COMPANY_NAME = "companyName";

    public static Placeholder as(String placeholder, String replacement) {
        return new Placeholder(placeholder, replacement);
    }

    public static Placeholder personName(String replacement) {
        return new Placeholder(PERSON_NAME, replacement);
    }

    public static Placeholder companyName(String replacement) {
        return new Placeholder(COMPANY_NAME, replacement);
    }
}
