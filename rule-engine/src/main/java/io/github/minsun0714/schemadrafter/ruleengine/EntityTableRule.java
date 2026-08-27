package io.github.minsun0714.schemadrafter.ruleengine;

import io.github.minsun0714.schemadrafter.model.EntityCandidate;
import io.github.minsun0714.schemadrafter.model.RequirementFact;
import io.github.minsun0714.schemadrafter.model.SchemaDecision;
import java.util.List;
import java.util.Locale;

public class EntityTableRule implements DesignRule {

    @Override
    public boolean supports(RequirementFact fact) {
        return fact instanceof EntityCandidate;
    }

    @Override
    public List<SchemaDecision> apply(RequirementFact fact, SchemaContext context) {
        EntityCandidate entity = (EntityCandidate) fact;
        String tableName = toTableName(entity.name());
        context.ensureTable(tableName);
        return List.of(new SchemaDecision(
                "EntityTableRule",
                "%s -> %s table".formatted(entity.name(), tableName)
        ));
    }

    static String toTableName(String entityName) {
        String base = entityName.toLowerCase(Locale.ROOT);
        if (base.endsWith("s") || base.endsWith("x") || base.endsWith("z") || base.endsWith("ch") || base.endsWith("sh")) {
            return base + "es";
        }
        if (base.endsWith("y") && base.length() > 1) {
            char beforeY = base.charAt(base.length() - 2);
            if ("aeiou".indexOf(beforeY) < 0) {
                return base.substring(0, base.length() - 1) + "ies";
            }
        }
        return base + "s";
    }
}
