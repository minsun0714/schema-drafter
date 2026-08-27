package io.github.minsun0714.schemadrafter.model;

public record ForeignKey(String columnName, String referencesTable, String referencesColumn) {
}
