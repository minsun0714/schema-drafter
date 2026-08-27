package io.github.minsun0714.schemadrafter.common;

import java.util.List;
import java.util.stream.Collectors;

public record RequirementDocument(List<RequirementItem> items) {

    public RequirementDocument {
        items = List.copyOf(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String toMarkdown() {
        return items.stream()
                .map(item -> "- %s: %s".formatted(item.id(), item.text()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
