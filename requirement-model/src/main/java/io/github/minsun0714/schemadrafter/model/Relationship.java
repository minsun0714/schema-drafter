package io.github.minsun0714.schemadrafter.model;

public record Relationship(
        String sourceEntity,
        String targetEntity,
        RelationshipType type,
        String sourceRequirementId
) implements RequirementFact {

    public Relationship {
        if (sourceEntity == null || sourceEntity.isBlank()) {
            throw new IllegalArgumentException("Source entity must not be blank");
        }
        if (targetEntity == null || targetEntity.isBlank()) {
            throw new IllegalArgumentException("Target entity must not be blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("Relationship type must not be null");
        }
        if (sourceRequirementId == null || sourceRequirementId.isBlank()) {
            throw new IllegalArgumentException("Source requirement id must not be blank");
        }
    }
}
