package io.github.minsun0714.schemadrafter.rules;

import io.github.minsun0714.schemadrafter.common.ColumnDraft;
import io.github.minsun0714.schemadrafter.common.RequirementAnalysis;
import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import io.github.minsun0714.schemadrafter.common.SchemaDraft;
import io.github.minsun0714.schemadrafter.common.TableDraft;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatabaseDesignRuleEngine {

    public SchemaDraft design(RequirementDocument requirementDocument, RequirementAnalysis analysis) {
        Map<String, TableDraft> tables = new LinkedHashMap<>();
        List<String> rulesApplied = new ArrayList<>();

        for (String entity : analysis.candidateEntities()) {
            registerEntity(entity, tables, rulesApplied);
        }

        for (var item : requirementDocument.items()) {
            String normalized = item.text().toLowerCase(Locale.ROOT);
            if (normalized.contains("inventory") || normalized.contains("재고")) {
                addIfAbsent(
                        tables,
                        "inventories",
                        new TableDraft("inventories", List.of(
                                column("id", "bigint", false),
                                column("product_id", "bigint", false),
                                column("quantity", "integer", false),
                                column("updated_at", "timestamp", false)
                        ))
                );
                rulesApplied.add(item.id() + " -> inventory tracking table");
            }
        }

        if (tables.isEmpty()) {
            tables.put("requirement_items", new TableDraft("requirement_items", List.of(
                    column("id", "bigint", false),
                    column("requirement_code", "varchar(20)", false),
                    column("raw_text", "text", false)
            )));
            rulesApplied.add("Fallback -> requirement_items catalog table");
        }

        return new SchemaDraft(List.copyOf(tables.values()), rulesApplied);
    }

    private static void registerEntity(String entity, Map<String, TableDraft> tables, List<String> rulesApplied) {
        switch (entity.toLowerCase(Locale.ROOT)) {
            case "user" -> {
                addIfAbsent(tables, "users", new TableDraft("users", List.of(
                        column("id", "bigint", false),
                        column("email", "varchar(255)", false),
                        column("name", "varchar(100)", false),
                        column("created_at", "timestamp", false)
                )));
                rulesApplied.add("Entity User -> users table");
            }
            case "order" -> {
                addIfAbsent(tables, "orders", new TableDraft("orders", List.of(
                        column("id", "bigint", false),
                        column("user_id", "bigint", false),
                        column("status", "varchar(30)", false),
                        column("created_at", "timestamp", false)
                )));
                rulesApplied.add("Entity Order -> orders table");
            }
            case "product" -> {
                addIfAbsent(tables, "products", new TableDraft("products", List.of(
                        column("id", "bigint", false),
                        column("name", "varchar(255)", false),
                        column("price", "decimal(12,2)", false),
                        column("created_at", "timestamp", false)
                )));
                rulesApplied.add("Entity Product -> products table");
            }
            case "requirementcatalog" -> {
                addIfAbsent(tables, "requirement_items", new TableDraft("requirement_items", List.of(
                        column("id", "bigint", false),
                        column("requirement_code", "varchar(20)", false),
                        column("raw_text", "text", false)
                )));
                rulesApplied.add("Entity RequirementCatalog -> requirement_items table");
            }
            default -> {
            }
        }
    }

    private static void addIfAbsent(Map<String, TableDraft> tables, String key, TableDraft tableDraft) {
        tables.putIfAbsent(key, tableDraft);
    }

    private static ColumnDraft column(String name, String type, boolean nullable) {
        return new ColumnDraft(name, type, nullable);
    }
}
