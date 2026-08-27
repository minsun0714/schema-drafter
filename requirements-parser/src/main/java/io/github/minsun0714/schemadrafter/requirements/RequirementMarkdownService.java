package io.github.minsun0714.schemadrafter.requirements;

import io.github.minsun0714.schemadrafter.common.RequirementDocument;
import io.github.minsun0714.schemadrafter.common.RequirementItem;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RequirementMarkdownService {

    private static final Pattern BULLET_PREFIX = Pattern.compile("^(?:[-*+]|\\d+[.)])\\s+");

    public RequirementDocument extract(String specification) {
        if (specification == null || specification.isBlank()) {
            return new RequirementDocument(List.of());
        }

        List<String> rawItems = new ArrayList<>();
        StringBuilder paragraph = new StringBuilder();

        for (String line : specification.replace("\r\n", "\n").split("\n")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                flushParagraph(rawItems, paragraph);
                continue;
            }
            if (BULLET_PREFIX.matcher(trimmed).find()) {
                flushParagraph(rawItems, paragraph);
                rawItems.add(BULLET_PREFIX.matcher(trimmed).replaceFirst("").trim());
                continue;
            }
            if (!paragraph.isEmpty()) {
                paragraph.append(' ');
            }
            paragraph.append(trimmed);
        }

        flushParagraph(rawItems, paragraph);

        List<RequirementItem> items = new ArrayList<>();
        for (int index = 0; index < rawItems.size(); index++) {
            items.add(new RequirementItem("REQ-%03d".formatted(index + 1), rawItems.get(index)));
        }
        return new RequirementDocument(items);
    }

    public String generateMarkdown(String specification) {
        return extract(specification).toMarkdown();
    }

    private static void flushParagraph(List<String> rawItems, StringBuilder paragraph) {
        if (paragraph.isEmpty()) {
            return;
        }
        rawItems.add(paragraph.toString());
        paragraph.setLength(0);
    }
}
