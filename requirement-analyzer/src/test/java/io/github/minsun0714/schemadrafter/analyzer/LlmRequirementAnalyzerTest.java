package io.github.minsun0714.schemadrafter.analyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.minsun0714.schemadrafter.model.RelationshipType;
import io.github.minsun0714.schemadrafter.model.Requirement;
import java.util.List;
import org.junit.jupiter.api.Test;

class LlmRequirementAnalyzerTest {

    private final RequirementAnalyzer analyzer = new LlmRequirementAnalyzer(new StubLlmClient());

    @Test
    void convertsRequirementsIntoEntityAndRelationshipIr() {
        var analysis = analyzer.analyze(List.of(
                new Requirement("REQ-001", "사용자는 여러 배송지를 등록할 수 있다.")
        ));

        assertTrue(analysis.llmPrompt().contains("REQ-001"));
        assertTrue(analysis.entityCandidates().stream().anyMatch(entity -> entity.name().equals("User")));
        assertTrue(analysis.entityCandidates().stream().anyMatch(entity -> entity.name().equals("Address")));
        assertEquals(RelationshipType.ONE_TO_MANY, analysis.relationships().getFirst().type());
        assertEquals("REQ-001", analysis.relationships().getFirst().sourceRequirementId());
    }
}
