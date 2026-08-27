package io.github.minsun0714.schemadrafter.app;

import io.github.minsun0714.schemadrafter.common.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.common.SchemaDraft;

public record DraftSchemaResponse(
        String requirementsMarkdown,
        RequirementAnalysis analysis,
        SchemaDraft schemaDraft
) {
}
