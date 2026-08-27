plugins {
    `java-library`
}

dependencies {
    api(project(":requirement-model"))
    api(platform("org.springframework.ai:spring-ai-bom:2.0.0"))
    api("org.springframework.ai:spring-ai-model")
}
