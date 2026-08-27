package io.github.minsun0714.schemadrafter.requirements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import org.junit.jupiter.api.Test;

class RequirementMarkdownServiceTest {

    private final RequirementMarkdownService service = new RequirementMarkdownService();

    @Test
    void createsReqPrefixedMarkdownFromBulletsAndParagraphs() {
        String specification = """
                - 회원은 이메일로 가입할 수 있다.
                - 주문은 사용자와 연결된다.

                상품은 재고를 관리해야 한다.
                """;

        RequirementDocument document = service.extract(specification);

        assertEquals(3, document.items().size());
        assertEquals("REQ-001", document.items().getFirst().id());
        assertEquals(
                """
                - REQ-001: 회원은 이메일로 가입할 수 있다.
                - REQ-002: 주문은 사용자와 연결된다.
                - REQ-003: 상품은 재고를 관리해야 한다.
                """.trim(),
                document.toMarkdown());
    }
}
