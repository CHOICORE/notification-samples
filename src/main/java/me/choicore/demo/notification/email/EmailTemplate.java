package me.choicore.demo.notification.email;

import me.choicore.demo.notification.AbstractTemplate;

public class EmailTemplate extends AbstractTemplate {
    private String subject;
    private String body;

    public EmailTemplate(String subject, String body) {
        super(subject + body);
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return this.subject;
    }


    @Override
    public String getTemplate() {
        return this.body;
    }
}
