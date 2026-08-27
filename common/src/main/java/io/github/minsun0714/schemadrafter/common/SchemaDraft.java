package io.github.minsun0714.schemadrafter.common;

import java.util.List;

public record SchemaDraft(List<TableDraft> tables, List<String> rulesApplied) {

    public SchemaDraft {
        tables = List.copyOf(tables);
        rulesApplied = List.copyOf(rulesApplied);
    }
}
