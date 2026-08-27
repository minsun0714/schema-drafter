package io.github.minsun0714.schemadrafter.common;

import java.util.List;

public record TableDraft(String name, List<ColumnDraft> columns) {

    public TableDraft {
        columns = List.copyOf(columns);
    }
}
