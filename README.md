# schema-drafter

Spring Boot 4.x, Spring AI 2.0, Gradle 멀티모듈 기반의 DB 설계 초안 프로젝트입니다.

## 모듈 구조

```text
root
├── application          # Spring Boot 실행 / API / orchestration
├── requirement-model    # Requirement, RequirementAnalysis(IR), Relationship, DatabaseSchema
├── requirement-analyzer # LLM 연결 + 요구사항 분석
└── rule-engine          # IR -> 결정론적 DB 설계 규칙 적용
```

## 설계 원칙

- `application`만 Spring Boot 실행 애플리케이션입니다.
- `requirement-analyzer`와 `rule-engine`은 서로를 직접 참조하지 않습니다.
- 두 모듈 사이의 계약은 `requirement-model`의 `RequirementAnalysis` IR 입니다.
- LLM은 자연어 요구사항을 정형화된 IR로 변환하고, Rule Engine은 해당 IR에 결정론적 설계 규칙을 적용하여 DB Schema를 생성합니다.

## 현재 파이프라인

```text
POST /api/v1/design
요구사항 텍스트/MD
    ↓
RequirementDraftService
    ↓
REQ-001 형식 Requirement 목록
    ↓
RequirementAnalyzer
    ↓
RequirementAnalysis(IR)
    ↓
RuleEngine
    ↓
DatabaseSchema
    ↓
Response
```

## 포함된 초안 구성

- `RequirementAnalyzer` 인터페이스와 `LlmRequirementAnalyzer` 구현체
- `DesignRule` 인터페이스
- `EntityTableRule`, `OneToManyRule` 규칙 초안
- `RequirementAnalysis`, `EntityCandidate`, `Relationship`, `DatabaseSchema` 등 공통 IR/모델
- 요구사항 입력을 `REQ-001` 형식으로 정규화하는 `RequirementDraftService`

## 실행

```bash
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64 ./gradlew test
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64 ./gradlew :application:bootRun
```

## API 예시

`POST /api/v1/design`

```json
{
  "requirements": "사용자는 여러 배송지를 등록할 수 있다.\n\n주문은 상품과 연결된다."
}
```
