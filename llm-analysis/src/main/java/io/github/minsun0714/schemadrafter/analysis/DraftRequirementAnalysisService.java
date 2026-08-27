package io.github.minsun0714.schemadrafter.analysis;

import io.github.minsun0714.schemadrafter.common.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.ai.chat.prompt.PromptTemplate;

public class DraftRequirementAnalysisService implements RequirementAnalysisService {

    private static final PromptTemplate PROMPT_TEMPLATE = new PromptTemplate("""
            You are a senior product analyst.
            Review the numbered requirements below and return:
            1. A concise summary
            2. Candidate domain entities
            3. Missing assumptions or ambiguities
            4. Constraints that affect relational database design

            Requirements:
            {requirementsMarkdown}
            """);

    @Override
    public RequirementAnalysis analyze(RequirementDocument requirementDocument) {
        String markdown = requirementDocument.toMarkdown();
        String prompt = PROMPT_TEMPLATE.render(Map.of("requirementsMarkdown", markdown));

        LinkedHashSet<String> entities = new LinkedHashSet<>();
        for (var item : requirementDocument.items()) {
            String normalized = item.text().toLowerCase(Locale.ROOT);
            if (normalized.contains("user") || normalized.contains("회원") || normalized.contains("사용자")) {
                entities.add("User");
            }
            if (normalized.contains("order") || normalized.contains("주문")) {
                entities.add("Order");
            }
            if (normalized.contains("product") || normalized.contains("상품")) {
                entities.add("Product");
            }
        }
        if (entities.isEmpty() && !requirementDocument.isEmpty()) {
            entities.add("RequirementCatalog");
        }

        String summary = requirementDocument.isEmpty()
                ? "No requirements were extracted yet."
                : "%d requirements extracted for downstream LLM analysis and rule-based schema drafting."
                .formatted(requirementDocument.items().size());

        return new RequirementAnalysis(
                summary,
                List.copyOf(entities),
                List.of(
                        "Validate entity relationships with an LLM response before generating migrations.",
                        "Use rule-engine output as the authoritative schema draft and keep LLM output advisory."
                ),
                prompt
        );
    }
}
