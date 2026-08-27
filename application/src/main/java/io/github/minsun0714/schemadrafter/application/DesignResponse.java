package io.github.minsun0714.schemadrafter.application;

import io.github.minsun0714.schemadrafter.model.DatabaseSchema;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;

public record DesignResponse(
        String requirementsMarkdown,
        RequirementAnalysis analysis,
        DatabaseSchema schema
) {

    public static DesignResponse from(DatabaseDesignResult result) {
        return new DesignResponse(result.requirementsMarkdown(), result.analysis(), result.schema());
    }
}
