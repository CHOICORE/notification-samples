package me.choicore.demo.notification.template;

public interface TemplateRepository {
    Template getTemplateByName(String name);

    Template getTemplateById(Long id);

    Long addTemplate(Template template);
}
