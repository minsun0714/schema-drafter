package io.github.minsun0714.schemadrafter.model;

import java.util.List;

public record DatabaseSchema(List<TableSchema> tables, List<SchemaDecision> decisions) {

    public DatabaseSchema {
        tables = List.copyOf(tables);
        decisions = List.copyOf(decisions);
    }
}
