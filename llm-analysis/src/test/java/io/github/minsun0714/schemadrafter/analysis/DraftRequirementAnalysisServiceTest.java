package io.github.minsun0714.schemadrafter.analysis;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import io.github.minsun0714.schemadrafter.common.RequirementItem;
import java.util.List;
import org.junit.jupiter.api.Test;

class DraftRequirementAnalysisServiceTest {

    private final DraftRequirementAnalysisService service = new DraftRequirementAnalysisService();

    @Test
    void buildsAnLlmPromptAndCandidateEntities() {
        RequirementDocument document = new RequirementDocument(List.of(
                new RequirementItem("REQ-001", "사용자는 상품을 주문할 수 있다.")
        ));

        var analysis = service.analyze(document);

        assertTrue(analysis.llmPrompt().contains("REQ-001"));
        assertTrue(analysis.candidateEntities().contains("User"));
        assertTrue(analysis.candidateEntities().contains("Order"));
        assertTrue(analysis.candidateEntities().contains("Product"));
    }
}
