package io.github.minsun0714.schemadrafter.common;

import java.util.List;

public record RequirementAnalysis(
        String summary,
        List<String> candidateEntities,
        List<String> designNotes,
        String llmPrompt
) {

    public RequirementAnalysis {
        candidateEntities = List.copyOf(candidateEntities);
        designNotes = List.copyOf(designNotes);
    }
}
