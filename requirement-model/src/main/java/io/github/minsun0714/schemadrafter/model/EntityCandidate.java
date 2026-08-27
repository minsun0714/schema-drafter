package io.github.minsun0714.schemadrafter.model;

public record EntityCandidate(String name, String sourceRequirementId) implements RequirementFact {

    public EntityCandidate {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Entity candidate name must not be blank");
        }
        if (sourceRequirementId == null || sourceRequirementId.isBlank()) {
            throw new IllegalArgumentException("Source requirement id must not be blank");
        }
    }
}
