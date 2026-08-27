package io.github.minsun0714.schemadrafter.application;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/design")
public class DatabaseDesignController {

    private final DatabaseDesignUseCase databaseDesignUseCase;

    public DatabaseDesignController(DatabaseDesignUseCase databaseDesignUseCase) {
        this.databaseDesignUseCase = databaseDesignUseCase;
    }

    @PostMapping
    public DesignResponse design(@RequestBody DesignRequest request) {
        return DesignResponse.from(databaseDesignUseCase.design(request.requirements()));
    }
}
