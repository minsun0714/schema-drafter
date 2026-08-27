package io.github.minsun0714.schemadrafter.model;

import java.util.List;
import java.util.stream.Stream;

public record RequirementAnalysis(
        List<Requirement> requirements,
        List<EntityCandidate> entityCandidates,
        List<Relationship> relationships,
        List<String> notes,
        String summary,
        String llmPrompt
) {

    public RequirementAnalysis {
        requirements = List.copyOf(requirements);
        entityCandidates = List.copyOf(entityCandidates);
        relationships = List.copyOf(relationships);
        notes = List.copyOf(notes);
    }

    public List<RequirementFact> facts() {
        return Stream.concat(entityCandidates.stream(), relationships.stream())
                .map(RequirementFact.class::cast)
                .toList();
    }
}
