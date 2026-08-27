package io.github.minsun0714.schemadrafter.app;

import io.github.minsun0714.schemadrafter.analysis.RequirementAnalysisService;
import io.github.minsun0714.schemadrafter.common.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import io.github.minsun0714.schemadrafter.common.SchemaDraft;
import io.github.minsun0714.schemadrafter.requirements.RequirementMarkdownService;
import io.github.minsun0714.schemadrafter.rules.DatabaseDesignRuleEngine;

public class SchemaDraftFacade {

    private final RequirementMarkdownService requirementMarkdownService;
    private final RequirementAnalysisService requirementAnalysisService;
    private final DatabaseDesignRuleEngine databaseDesignRuleEngine;

    public SchemaDraftFacade(
            RequirementMarkdownService requirementMarkdownService,
            RequirementAnalysisService requirementAnalysisService,
            DatabaseDesignRuleEngine databaseDesignRuleEngine
    ) {
        this.requirementMarkdownService = requirementMarkdownService;
        this.requirementAnalysisService = requirementAnalysisService;
        this.databaseDesignRuleEngine = databaseDesignRuleEngine;
    }

    public DraftSchemaResponse draft(String specification) {
        RequirementDocument requirementDocument = requirementMarkdownService.extract(specification);
        RequirementAnalysis analysis = requirementAnalysisService.analyze(requirementDocument);
        SchemaDraft schemaDraft = databaseDesignRuleEngine.design(requirementDocument, analysis);

        return new DraftSchemaResponse(requirementDocument.toMarkdown(), analysis, schemaDraft);
    }
}
