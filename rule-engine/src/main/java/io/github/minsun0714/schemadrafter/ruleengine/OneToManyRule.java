package io.github.minsun0714.schemadrafter.ruleengine;

import io.github.minsun0714.schemadrafter.model.ColumnSchema;
import io.github.minsun0714.schemadrafter.model.ForeignKey;
import io.github.minsun0714.schemadrafter.model.Relationship;
import io.github.minsun0714.schemadrafter.model.RelationshipType;
import io.github.minsun0714.schemadrafter.model.RequirementFact;
import io.github.minsun0714.schemadrafter.model.SchemaDecision;
import java.util.List;
import java.util.Locale;

public class OneToManyRule implements DesignRule {

    @Override
    public boolean supports(RequirementFact fact) {
        return fact instanceof Relationship relationship && relationship.type() == RelationshipType.ONE_TO_MANY;
    }

    @Override
    public List<SchemaDecision> apply(RequirementFact fact, SchemaContext context) {
        Relationship relationship = (Relationship) fact;
        String sourceTable = EntityTableRule.toTableName(relationship.sourceEntity());
        String targetTable = EntityTableRule.toTableName(relationship.targetEntity());
        String foreignKeyColumn = relationship.sourceEntity().toLowerCase(Locale.ROOT) + "_id";

        context.ensureTable(sourceTable);
        context.ensureTable(targetTable);
        context.ensureColumn(targetTable, new ColumnSchema(foreignKeyColumn, "bigint", false));
        context.ensureForeignKey(targetTable, new ForeignKey(foreignKeyColumn, sourceTable, "id"));

        return List.of(new SchemaDecision(
                "OneToManyRule",
                "%s.%s -> %s.id from %s".formatted(targetTable, foreignKeyColumn, sourceTable, relationship.sourceRequirementId())
        ));
    }
}
