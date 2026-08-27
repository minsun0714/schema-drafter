package io.github.minsun0714.schemadrafter.app;

import io.github.minsun0714.schemadrafter.analysis.DraftRequirementAnalysisService;
import io.github.minsun0714.schemadrafter.analysis.RequirementAnalysisService;
import io.github.minsun0714.schemadrafter.requirements.RequirementMarkdownService;
import io.github.minsun0714.schemadrafter.rules.DatabaseDesignRuleEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaDrafterConfiguration {

    @Bean
    RequirementMarkdownService requirementMarkdownService() {
        return new RequirementMarkdownService();
    }

    @Bean
    RequirementAnalysisService requirementAnalysisService() {
        return new DraftRequirementAnalysisService();
    }

    @Bean
    DatabaseDesignRuleEngine databaseDesignRuleEngine() {
        return new DatabaseDesignRuleEngine();
    }

    @Bean
    SchemaDraftFacade schemaDraftFacade(
            RequirementMarkdownService requirementMarkdownService,
            RequirementAnalysisService requirementAnalysisService,
            DatabaseDesignRuleEngine databaseDesignRuleEngine
    ) {
        return new SchemaDraftFacade(requirementMarkdownService, requirementAnalysisService, databaseDesignRuleEngine);
    }
}
