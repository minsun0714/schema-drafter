package io.github.minsun0714.schemadrafter.application;

import io.github.minsun0714.schemadrafter.model.DatabaseSchema;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;

public record DatabaseDesignResult(
        String requirementsMarkdown,
        RequirementAnalysis analysis,
        DatabaseSchema schema
) {
}
