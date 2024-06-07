package me.choicore.demo.notification;

import me.choicore.demo.notification.email.EmailTemplate;

public class PlaceholderResolver {
    private String replacePlaceHolders(String template, PlaceholderRegistry registry) {
        return registry.replace(template);
    }

    public Template resolve(Template template, PlaceholderRegistry registry) {
        if (!template.hasPlaceholders()) {
            return template;
        }

        if (template instanceof EmailTemplate emailTemplate) {
            String replacedSubject = replacePlaceHolders(emailTemplate.getSubject(), registry);
            String replacedBody = replacePlaceHolders(emailTemplate.getTemplate(), registry);
            return new EmailTemplate(replacedSubject, replacedBody);
        } else {
            throw new IllegalArgumentException("Unsupported template type");
        }
    }
}
