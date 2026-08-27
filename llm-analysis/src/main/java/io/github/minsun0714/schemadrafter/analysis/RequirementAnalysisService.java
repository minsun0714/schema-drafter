package io.github.minsun0714.schemadrafter.analysis;

import io.github.minsun0714.schemadrafter.common.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.common.RequirementDocument;

public interface RequirementAnalysisService {

    RequirementAnalysis analyze(RequirementDocument requirementDocument);
}
