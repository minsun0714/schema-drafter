package io.github.minsun0714.schemadrafter.ruleengine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.minsun0714.schemadrafter.model.EntityCandidate;
import io.github.minsun0714.schemadrafter.model.Relationship;
import io.github.minsun0714.schemadrafter.model.RelationshipType;
import io.github.minsun0714.schemadrafter.model.Requirement;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;
import java.util.List;
import org.junit.jupiter.api.Test;

class DeterministicRuleEngineTest {

    private final RuleEngine ruleEngine = new DeterministicRuleEngine(List.of(
            new EntityTableRule(),
            new OneToManyRule()
    ));

    @Test
    void createsForeignKeyFromOneToManyRelationship() {
        RequirementAnalysis analysis = new RequirementAnalysis(
                List.of(new Requirement("REQ-001", "사용자는 여러 배송지를 등록할 수 있다.")),
                List.of(
                        new EntityCandidate("User", "REQ-001"),
                        new EntityCandidate("Address", "REQ-001")
                ),
                List.of(new Relationship("User", "Address", RelationshipType.ONE_TO_MANY, "REQ-001")),
                List.of(),
                "draft",
                "prompt"
        );

        var schema = ruleEngine.design(analysis);

        assertTrue(schema.tables().stream().anyMatch(table -> table.name().equals("users")));
        assertTrue(schema.tables().stream().anyMatch(table -> table.name().equals("addresses")
                && table.columns().stream().anyMatch(column -> column.name().equals("user_id"))
                && table.foreignKeys().stream().anyMatch(foreignKey -> foreignKey.referencesTable().equals("users"))));
    }
}
