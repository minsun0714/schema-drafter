# schema-drafter

Spring Boot 4.x + Spring AI 2.0 + Gradle 멀티모듈 기반의 초안 프로젝트입니다.

## 모듈 구성

- `app`: REST API와 전체 오케스트레이션
- `common`: 요구사항/분석/스키마 초안 공용 모델
- `requirements-parser`: 요구사항 명세를 `REQ-001` 형식의 Markdown 목록으로 변환
- `llm-analysis`: Spring AI `PromptTemplate` 기반 요구사항 분석 프롬프트 초안 생성
- `db-rule-engine`: 요구사항/분석 결과를 바탕으로 DB 스키마 초안을 생성하는 Rule Engine

## 현재 초안 흐름

1. 요구사항 명세 입력
2. `requirements-parser`가 `REQ-001` 형태 Markdown 생성
3. `llm-analysis`가 LLM 분석용 프롬프트와 후보 엔티티 초안을 생성
4. `db-rule-engine`가 실제 DB 스키마 초안을 규칙 기반으로 생성

## 실행

```bash
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64 ./gradlew test
JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64 ./gradlew :app:bootRun
```

## API 예시

`POST /api/schema-drafts`

```json
{
  "specification": "- 사용자는 이메일로 가입한다.\n- 상품을 주문할 수 있다."
}
```
