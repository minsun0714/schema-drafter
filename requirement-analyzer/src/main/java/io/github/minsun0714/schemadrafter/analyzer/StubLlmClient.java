package io.github.minsun0714.schemadrafter.analyzer;

public class StubLlmClient implements LlmClient {

    @Override
    public String complete(String prompt) {
        return "Stubbed LLM analysis for draft scaffold.";
    }
}
