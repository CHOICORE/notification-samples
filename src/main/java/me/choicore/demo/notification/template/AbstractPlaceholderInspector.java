package me.choicore.demo.notification.template;

import java.util.List;

public class AbstractPlaceholderInspector implements PlaceholderInspector {

    private final List<PlaceholderInspector> inspectors;

    public AbstractPlaceholderInspector(List<PlaceholderInspector> inspectors) {
        this.inspectors = inspectors;
    }

    @Override
    public boolean inspect(String content) {

        for (PlaceholderInspector inspector : inspectors) {
            return inspector.inspect(content);
        }

        return false;
    }
}
