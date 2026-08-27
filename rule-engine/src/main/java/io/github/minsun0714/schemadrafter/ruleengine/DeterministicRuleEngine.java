package io.github.minsun0714.schemadrafter.ruleengine;

import io.github.minsun0714.schemadrafter.model.DatabaseSchema;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.model.RequirementFact;
import java.util.List;

public class DeterministicRuleEngine implements RuleEngine {

    private final List<DesignRule> rules;

    public DeterministicRuleEngine(List<DesignRule> rules) {
        this.rules = List.copyOf(rules);
    }

    @Override
    public DatabaseSchema design(RequirementAnalysis analysis) {
        SchemaContext context = new SchemaContext();
        for (RequirementFact fact : analysis.facts()) {
            for (DesignRule rule : rules) {
                if (rule.supports(fact)) {
                    context.addDecisions(rule.apply(fact, context));
                }
            }
        }
        return context.toSchema();
    }
}
