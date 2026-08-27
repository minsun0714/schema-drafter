package io.github.minsun0714.schemadrafter.model;

public record Requirement(String id, String text) {

    public Requirement {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Requirement id must not be blank");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Requirement text must not be blank");
        }
    }
}
