package io.github.minsun0714.schemadrafter.app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schema-drafts")
public class SchemaDraftController {

    private final SchemaDraftFacade schemaDraftFacade;

    public SchemaDraftController(SchemaDraftFacade schemaDraftFacade) {
        this.schemaDraftFacade = schemaDraftFacade;
    }

    @PostMapping
    public DraftSchemaResponse draft(@RequestBody DraftSchemaRequest request) {
        return schemaDraftFacade.draft(request.specification());
    }
}
