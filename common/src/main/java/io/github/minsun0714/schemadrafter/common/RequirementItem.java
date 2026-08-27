package io.github.minsun0714.schemadrafter.common;

public record RequirementItem(String id, String text) {

    public RequirementItem {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Requirement id must not be blank");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Requirement text must not be blank");
        }
    }
}
