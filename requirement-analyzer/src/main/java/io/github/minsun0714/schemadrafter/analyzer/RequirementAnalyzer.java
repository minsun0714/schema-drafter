package io.github.minsun0714.schemadrafter.analyzer;

import io.github.minsun0714.schemadrafter.model.Requirement;
import io.github.minsun0714.schemadrafter.model.RequirementAnalysis;
import java.util.List;

public interface RequirementAnalyzer {

    RequirementAnalysis analyze(List<Requirement> requirements);
}
