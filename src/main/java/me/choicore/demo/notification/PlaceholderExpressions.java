package me.choicore.demo.notification;

public class PlaceholderExpressions {

    public static final String PERSON_NAME = "personName";
    public static final String COMPANY_NAME = "companyName";

    public static PlaceholderExpression as(String placeholder, String replacement) {
        return new PlaceholderExpression(placeholder, replacement);
    }

    public static PlaceholderExpression personName(String replacement) {
        return new PlaceholderExpression(PERSON_NAME, replacement);
    }

    public static PlaceholderExpression companyName(String replacement) {
        return new PlaceholderExpression(COMPANY_NAME, replacement);
    }
}
