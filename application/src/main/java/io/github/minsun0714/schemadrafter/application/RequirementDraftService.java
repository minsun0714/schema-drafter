package io.github.minsun0714.schemadrafter.application;

import io.github.minsun0714.schemadrafter.model.Requirement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RequirementDraftService {

    private static final Pattern BULLET_PREFIX = Pattern.compile("^(?:[-*+]|\\d+[.)])\\s+");

    public List<Requirement> draftRequirements(String rawRequirements) {
        if (rawRequirements == null || rawRequirements.isBlank()) {
            return List.of();
        }

        List<String> rawItems = new ArrayList<>();
        StringBuilder paragraph = new StringBuilder();

        for (String line : rawRequirements.replace("\r\n", "\n").split("\n")) {
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

        List<Requirement> requirements = new ArrayList<>();
        for (int index = 0; index < rawItems.size(); index++) {
            requirements.add(new Requirement("REQ-%03d".formatted(index + 1), rawItems.get(index)));
        }
        return List.copyOf(requirements);
    }

    public String toMarkdown(List<Requirement> requirements) {
        return requirements.stream()
                .map(requirement -> "- %s: %s".formatted(requirement.id(), requirement.text()))
                .reduce((left, right) -> left + System.lineSeparator() + right)
                .orElse("");
    }

    private static void flushParagraph(List<String> rawItems, StringBuilder paragraph) {
        if (paragraph.isEmpty()) {
            return;
        }
        rawItems.add(paragraph.toString());
        paragraph.setLength(0);
    }
}
