package io.github.minsun0714.schemadrafter.application;

import io.github.minsun0714.schemadrafter.analyzer.LlmRequirementAnalyzer;
import io.github.minsun0714.schemadrafter.analyzer.RequirementAnalyzer;
import io.github.minsun0714.schemadrafter.analyzer.StubLlmClient;
import io.github.minsun0714.schemadrafter.ruleengine.DeterministicRuleEngine;
import io.github.minsun0714.schemadrafter.ruleengine.EntityTableRule;
import io.github.minsun0714.schemadrafter.ruleengine.OneToManyRule;
import io.github.minsun0714.schemadrafter.ruleengine.RuleEngine;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    RequirementDraftService requirementDraftService() {
        return new RequirementDraftService();
    }

    @Bean
    RequirementAnalyzer requirementAnalyzer() {
        return new LlmRequirementAnalyzer(new StubLlmClient());
    }

    @Bean
    RuleEngine ruleEngine() {
        return new DeterministicRuleEngine(List.of(
                new EntityTableRule(),
                new OneToManyRule()
        ));
    }

    @Bean
    DatabaseDesignUseCase databaseDesignUseCase(
            RequirementDraftService requirementDraftService,
            RequirementAnalyzer requirementAnalyzer,
            RuleEngine ruleEngine
    ) {
        return new DatabaseDesignUseCase(requirementDraftService, requirementAnalyzer, ruleEngine);
    }
}
