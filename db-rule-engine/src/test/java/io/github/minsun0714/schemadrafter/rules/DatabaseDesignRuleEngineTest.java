package io.github.minsun0714.schemadrafter.rules;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.minsun0714.schemadrafter.common.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import io.github.minsun0714.schemadrafter.common.RequirementItem;
import java.util.List;
import org.junit.jupiter.api.Test;

class DatabaseDesignRuleEngineTest {

    private final DatabaseDesignRuleEngine engine = new DatabaseDesignRuleEngine();

    @Test
    void createsDomainTablesFromAnalyzedEntities() {
        RequirementDocument document = new RequirementDocument(List.of(
                new RequirementItem("REQ-001", "사용자는 상품을 주문할 수 있다."),
                new RequirementItem("REQ-002", "상품 재고를 관리해야 한다.")
        ));
        RequirementAnalysis analysis = new RequirementAnalysis(
                "draft",
                List.of("User", "Order", "Product"),
                List.of(),
                "prompt"
        );

        var schemaDraft = engine.design(document, analysis);

        assertTrue(schemaDraft.tables().stream().anyMatch(table -> table.name().equals("users")));
        assertTrue(schemaDraft.tables().stream().anyMatch(table -> table.name().equals("orders")));
        assertTrue(schemaDraft.tables().stream().anyMatch(table -> table.name().equals("products")));
        assertTrue(schemaDraft.tables().stream().anyMatch(table -> table.name().equals("inventories")));
    }
}
