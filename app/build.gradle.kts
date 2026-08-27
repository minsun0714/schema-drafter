plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.0"))

    implementation(project(":common"))
    implementation(project(":requirements-parser"))
    implementation(project(":llm-analysis"))
    implementation(project(":db-rule-engine"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
