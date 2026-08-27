package io.github.minsun0714.schemadrafter.ruleengine;

import io.github.minsun0714.schemadrafter.model.ColumnSchema;
import io.github.minsun0714.schemadrafter.model.DatabaseSchema;
import io.github.minsun0714.schemadrafter.model.ForeignKey;
import io.github.minsun0714.schemadrafter.model.SchemaDecision;
import io.github.minsun0714.schemadrafter.model.TableSchema;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SchemaContext {

    private final Map<String, MutableTable> tables = new LinkedHashMap<>();
    private final List<SchemaDecision> decisions = new ArrayList<>();

    public void ensureTable(String tableName) {
        tables.computeIfAbsent(tableName, ignored -> new MutableTable(tableName));
    }

    public void ensureColumn(String tableName, ColumnSchema columnSchema) {
        ensureTable(tableName);
        tables.get(tableName).addColumnIfAbsent(columnSchema);
    }

    public void ensureForeignKey(String tableName, ForeignKey foreignKey) {
        ensureTable(tableName);
        tables.get(tableName).addForeignKeyIfAbsent(foreignKey);
    }

    public void addDecisions(List<SchemaDecision> schemaDecisions) {
        decisions.addAll(schemaDecisions);
    }

    public DatabaseSchema toSchema() {
        return new DatabaseSchema(
                tables.values().stream().map(MutableTable::toTableSchema).toList(),
                List.copyOf(decisions)
        );
    }

    private static final class MutableTable {

        private final String name;
        private final List<ColumnSchema> columns = new ArrayList<>(List.of(
                new ColumnSchema("id", "bigint", false),
                new ColumnSchema("created_at", "timestamp", false)
        ));
        private final List<ForeignKey> foreignKeys = new ArrayList<>();

        private MutableTable(String name) {
            this.name = name;
        }

        private void addColumnIfAbsent(ColumnSchema columnSchema) {
            boolean exists = columns.stream().anyMatch(existing -> existing.name().equals(columnSchema.name()));
            if (!exists) {
                columns.add(columnSchema);
            }
        }

        private void addForeignKeyIfAbsent(ForeignKey foreignKey) {
            boolean exists = foreignKeys.stream().anyMatch(existing -> existing.columnName().equals(foreignKey.columnName())
                    && existing.referencesTable().equals(foreignKey.referencesTable())
                    && existing.referencesColumn().equals(foreignKey.referencesColumn()));
            if (!exists) {
                foreignKeys.add(foreignKey);
            }
        }

        private TableSchema toTableSchema() {
            return new TableSchema(name, List.copyOf(columns), List.copyOf(foreignKeys));
        }
    }
}
