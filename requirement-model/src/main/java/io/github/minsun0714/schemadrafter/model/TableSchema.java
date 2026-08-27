package io.github.minsun0714.schemadrafter.model;

import java.util.List;

public record TableSchema(String name, List<ColumnSchema> columns, List<ForeignKey> foreignKeys) {

    public TableSchema {
        columns = List.copyOf(columns);
        foreignKeys = List.copyOf(foreignKeys);
    }
}
