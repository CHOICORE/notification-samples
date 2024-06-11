package me.choicore.demo.notification.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TestTemplateRepository implements TemplateRepository {
    private final Map<Long, Template> database = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Template getTemplateById(Long id) {
        return database.get(id);
    }

    @Override
    public Long addTemplate(Template template) {
        final long id = sequence.getAndIncrement();
        database.put(id, template);
        return id;
    }

    @Override
    public Template getTemplateByName(String name) {
        return database.values().stream()
                .filter(template -> template.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Template not found"));
    }
}
