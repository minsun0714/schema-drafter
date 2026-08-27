package io.github.minsun0714.schemadrafter.ruleengine;

import io.github.minsun0714.schemadrafter.model.DatabaseSchema;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;

public interface RuleEngine {

    DatabaseSchema design(RequirementAnalysis analysis);
}
