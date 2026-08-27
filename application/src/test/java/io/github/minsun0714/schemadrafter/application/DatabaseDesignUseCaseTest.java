package io.github.minsun0714.schemadrafter.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.minsun0714.schemadrafter.analyzer.LlmRequirementAnalyzer;
import io.github.minsun0714.schemadrafter.analyzer.StubLlmClient;
import io.github.minsun0714.schemadrafter.ruleengine.DeterministicRuleEngine;
import io.github.minsun0714.schemadrafter.ruleengine.EntityTableRule;
import io.github.minsun0714.schemadrafter.ruleengine.OneToManyRule;
import org.junit.jupiter.api.Test;

class DatabaseDesignUseCaseTest {

    @Test
    void orchestratesRequirementDraftAnalysisAndSchemaDesign() {
        DatabaseDesignUseCase useCase = new DatabaseDesignUseCase(
                new RequirementDraftService(),
                new LlmRequirementAnalyzer(new StubLlmClient()),
                new DeterministicRuleEngine(java.util.List.of(new EntityTableRule(), new OneToManyRule()))
        );

        var result = useCase.design("""
                사용자는 여러 배송지를 등록할 수 있다.

                주문은 상품과 연결된다.
                """);

        assertTrue(result.requirementsMarkdown().contains("REQ-001"));
        assertTrue(result.analysis().relationships().stream().anyMatch(relationship -> relationship.sourceEntity().equals("User")
                && relationship.targetEntity().equals("Address")));
        assertTrue(result.schema().tables().stream().anyMatch(table -> table.name().equals("addresses")
                && table.columns().stream().anyMatch(column -> column.name().equals("user_id"))));
    }
}
