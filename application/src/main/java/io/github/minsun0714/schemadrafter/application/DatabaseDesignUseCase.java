package io.github.minsun0714.schemadrafter.application;

import io.github.minsun0714.schemadrafter.analyzer.RequirementAnalyzer;
import io.github.minsun0714.schemadrafter.model.DatabaseSchema;
import io.github.minsun0714.schemadrafter.model.Requirement;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.ruleengine.RuleEngine;
import java.util.List;

public class DatabaseDesignUseCase {

    private final RequirementDraftService requirementDraftService;
    private final RequirementAnalyzer requirementAnalyzer;
    private final RuleEngine ruleEngine;

    public DatabaseDesignUseCase(
            RequirementDraftService requirementDraftService,
            RequirementAnalyzer requirementAnalyzer,
            RuleEngine ruleEngine
    ) {
        this.requirementDraftService = requirementDraftService;
        this.requirementAnalyzer = requirementAnalyzer;
        this.ruleEngine = ruleEngine;
    }

    public DatabaseDesignResult design(String rawRequirements) {
        List<Requirement> requirements = requirementDraftService.draftRequirements(rawRequirements);
        RequirementAnalysis analysis = requirementAnalyzer.analyze(requirements);
        DatabaseSchema schema = ruleEngine.design(analysis);
        return new DatabaseDesignResult(requirementDraftService.toMarkdown(requirements), analysis, schema);
    }
}
