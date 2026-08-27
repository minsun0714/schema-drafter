package io.github.minsun0714.schemadrafter.ruleengine;

import io.github.minsun0714.schemadrafter.model.RequirementFact;
import io.github.minsun0714.schemadrafter.model.SchemaDecision;
import java.util.List;

public interface DesignRule {

    boolean supports(RequirementFact fact);

    List<SchemaDecision> apply(RequirementFact fact, SchemaContext context);
}
