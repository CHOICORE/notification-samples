package me.choicore.demo.notification.template;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleTemplateRegistry implements TemplateRegistry {
    private final TemplateRepository templateRepository;

    @Override
    public Template getTemplate(String name) {
        return templateRepository.getTemplateByName(name);
    }

    @Override
    public Template getTemplate(Long id) {
        return templateRepository.getTemplateById(id);
    }

    @Override
    public Long registerTemplate(Template template) {
        return templateRepository.addTemplate(template);
    }
}
