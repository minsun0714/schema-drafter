package io.github.minsun0714.schemadrafter.analyzer;

import io.github.minsun0714.schemadrafter.model.EntityCandidate;
import io.github.minsun0714.schemadrafter.model.Relationship;
import io.github.minsun0714.schemadrafter.model.RelationshipType;
import io.github.minsun0714.schemadrafter.model.Requirement;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ai.chat.prompt.PromptTemplate;

public class LlmRequirementAnalyzer implements RequirementAnalyzer {

    private static final PromptTemplate PROMPT_TEMPLATE = new PromptTemplate("""
            You are an expert requirements analyst.
            Convert the requirements markdown into a structured intermediate representation (IR).
            Extract entities, relationships, constraints, and ambiguities that affect relational database design.

            Requirements:
            {requirementsMarkdown}
            """);

    private final LlmClient llmClient;

    public LlmRequirementAnalyzer(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public RequirementAnalysis analyze(List<Requirement> requirements) {
        String requirementsMarkdown = requirements.stream()
                .map(requirement -> "- %s: %s".formatted(requirement.id(), requirement.text()))
                .collect(Collectors.joining(System.lineSeparator()));
        String prompt = PROMPT_TEMPLATE.render(Map.of("requirementsMarkdown", requirementsMarkdown));
        String llmDraft = llmClient.complete(prompt);

        Map<String, EntityCandidate> entities = new LinkedHashMap<>();
        Map<String, Relationship> relationships = new LinkedHashMap<>();

        for (Requirement requirement : requirements) {
            String normalized = requirement.text().toLowerCase(Locale.ROOT);

            registerEntityIfPresent(entities, normalized, requirement, "user", "사용자", "회원");
            registerEntityIfPresent(entities, normalized, requirement, "address", "배송지", "주소");
            registerEntityIfPresent(entities, normalized, requirement, "order", "주문");
            registerEntityIfPresent(entities, normalized, requirement, "product", "상품", "제품");

            if ((containsAny(normalized, "사용자", "회원", "user"))
                    && containsAny(normalized, "배송지", "주소", "address")
                    && containsAny(normalized, "여러", "multiple", "many")) {
                relationships.putIfAbsent(
                        "User->Address:ONE_TO_MANY",
                        new Relationship("User", "Address", RelationshipType.ONE_TO_MANY, requirement.id())
                );
            }

            if (containsAny(normalized, "주문", "order")
                    && containsAny(normalized, "상품", "제품", "product")) {
                relationships.putIfAbsent(
                        "Order->Product:MANY_TO_MANY",
                        new Relationship("Order", "Product", RelationshipType.MANY_TO_MANY, requirement.id())
                );
            }
        }

        String summary = requirements.isEmpty()
                ? "No requirements were provided."
                : "%d requirements converted into IR for deterministic schema design."
                .formatted(requirements.size());

        return new RequirementAnalysis(
                requirements,
                List.copyOf(entities.values()),
                List.copyOf(relationships.values()),
                List.of(
                        "LLM output should remain advisory and reviewable.",
                        llmDraft
                ),
                summary,
                prompt
        );
    }

    private static void registerEntityIfPresent(
            Map<String, EntityCandidate> entities,
            String normalized,
            Requirement requirement,
            String canonicalName,
            String... keywords
    ) {
        if (containsAny(normalized, keywords)) {
            String entityName = canonicalName.substring(0, 1).toUpperCase(Locale.ROOT)
                    + canonicalName.substring(1).toLowerCase(Locale.ROOT);
            entities.putIfAbsent(entityName, new EntityCandidate(entityName, requirement.id()));
        }
    }

    private static boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }
}
