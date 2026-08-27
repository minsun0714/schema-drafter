package io.github.minsun0714.schemadrafter.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.minsun0714.schemadrafter.analysis.DraftRequirementAnalysisService;
import io.github.minsun0714.schemadrafter.requirements.RequirementMarkdownService;
import io.github.minsun0714.schemadrafter.rules.DatabaseDesignRuleEngine;
import org.junit.jupiter.api.Test;

class SchemaDraftFacadeTest {

    @Test
    void draftsMarkdownAnalysisAndSchemaTogether() {
        SchemaDraftFacade facade = new SchemaDraftFacade(
                new RequirementMarkdownService(),
                new DraftRequirementAnalysisService(),
                new DatabaseDesignRuleEngine()
        );

        var response = facade.draft("""
                - 사용자는 이메일로 가입한다.
                - 상품을 주문할 수 있다.
                """);

        assertTrue(response.requirementsMarkdown().contains("REQ-001"));
        assertTrue(response.analysis().llmPrompt().contains("Requirements:"));
        assertTrue(response.schemaDraft().tables().stream().anyMatch(table -> table.name().equals("users")));
    }
}
