package me.choicore.demo.notification.template;

public interface TemplateRegistry {
    Template getTemplate(String name);

    Template getTemplate(Long id);

    Long registerTemplate(Template template);
}
