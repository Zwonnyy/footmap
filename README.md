# FootMap

FootMap은 풋살 팀, 선수, 구장, 경기 매칭, 커뮤니티를 한 곳에서 관리하는 Spring Boot 기반 웹 애플리케이션입니다. 팀을 만들고 가입할 수 있으며, 풋살장 상세 정보와 지도 앱 연결, 경기 등록/신청/승인, 선수 랭킹, 게시판, Gemini AI 기반 문구 생성 기능을 제공합니다.

## 주요 기능

- 회원가입, 로그인, 내 정보 수정
- 선수 랭킹 및 선수 상세 스탯 조회
- 팀 생성, 팀 가입, 내 팀 목록, 팀 상세 조회
- 풋살장 목록 및 상세 정보 조회
- 풋살장 주소 기반 Naver/Kakao/Google 지도 연결
- 경기 등록, 경기 찾기, 경기 신청, 승인/거절, 내 경기 내역
- 커뮤니티 게시글 작성, 조회, 수정, 삭제
- Gemini AI 기반 게시글 요약, 표현 검사, 경기/팀 소개글 생성, 선수/팀 분석, 경기 후기 생성

## 기술 스택

- Java 11
- Spring Boot 2.7.6
- Spring Security
- Thymeleaf + Thymeleaf Layout Dialect
- MyBatis
- Spring Data JPA
- MySQL
- Gradle
- Gemini API

## 프로젝트 구조

```text
src/main/java/footmap/footmap_spring
├── controller      # 화면/API 요청 처리
├── service         # 비즈니스 로직
├── dao             # MyBatis Mapper 인터페이스
├── dto             # 화면/DB 전달 객체
├── Security        # 사용자 인증 처리
└── config          # Spring Security 설정

src/main/resources
├── templates       # Thymeleaf 화면
├── static/css      # 공통 스타일
├── mybatis/mapper  # MyBatis SQL XML
├── schema.sql      # 기본 테이블 생성
├── data.sql        # 기본 샘플 데이터
└── application.properties
```

## 실행 전 준비

### 1. MySQL 데이터베이스 생성

로컬 MySQL에 `db_footmap` 데이터베이스를 생성합니다.

```sql
CREATE DATABASE db_footmap DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

기본 접속 정보는 [application.properties](src/main/resources/application.properties)에 설정되어 있습니다.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_footmap?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=
```

앱 실행 시 `schema.sql`, `data.sql`이 실행되어 기본 테이블과 샘플 데이터가 준비됩니다.

### 2. Gemini API 키 설정

AI 기능을 실제 Gemini API로 사용하려면 환경변수에 키를 설정합니다.

```bash
export GEMINI_API_KEY=발급받은_키
```

모델 기본값은 무료 티어에 맞춰 `gemini-3.1-flash-lite`로 설정되어 있습니다.

```properties
gemini.model=${GEMINI_MODEL:gemini-3.1-flash-lite}
```

다른 모델을 쓰고 싶다면 아래처럼 실행 환경에서 바꿀 수 있습니다.

```bash
export GEMINI_MODEL=gemini-3.5-flash
```

`GEMINI_API_KEY`가 없으면 AI 기능은 외부 API를 호출하지 않고 로컬 fallback 문구로 동작합니다.

## 실행 방법

```bash
./gradlew bootRun
```

기본 접속 주소:

```text
http://localhost:8080/
```

8080 포트가 이미 사용 중이면 다른 포트로 실행할 수 있습니다.

```bash
./gradlew bootRun --args='--server.port=8081'
```

## 테스트

```bash
./gradlew test
```

## 주요 화면 경로

| 기능 | 경로 |
| --- | --- |
| 메인 | `/` |
| 로그인 | `/login` |
| 회원가입 | `/signUp` |
| 내 정보 | `/Mypage` |
| 선수 랭킹 | `/players/ranking` |
| 선수 상세 | `/players/detail?u_code=1` |
| 팀 목록 | `/Team/t_search` |
| 팀 생성 | `/Team/t_commit` |
| 내 팀 | `/Team/myteam` |
| 팀 상세 | `/Team/detail?t_code=1` |
| 경기 찾기 | `/game/search` |
| 경기 등록 | `/game/registerForm` |
| 내 경기 내역 | `/game/list` |
| AI 경기 후기 | `/game/reviewForm` |
| 게시판 | `/board/list` |
| 게시글 작성 | `/board/writeForm` |
| 풋살장 정보 | `/Page/FINFO` |
| 풋살장 상세 | `/Page/FINFO/detail?f_code=1` |
| 서비스 소개 | `/Page/s_intro` |
| 이용 규칙 | `/Page/s_rule` |

## AI 기능 사용 위치

- `/board/writeForm`: 게시글 요약 미리보기, 표현 검사
- `/board/view?idx=...`: 게시글 요약, 표현 검사 자동 표시
- `/game/registerForm`: 경기 소개글 생성
- `/Team/t_commit`: 팀 소개글 생성
- `/players/detail?u_code=...`: 선수 분석 자동 표시
- `/Team/detail?t_code=...`: 팀 추천 자동 표시
- `/game/search`: 매칭 추천 자동 표시
- `/game/reviewForm`: 경기 후기 생성

자동 표시 화면은 페이지 진입 시 API 호출이 발생할 수 있습니다. 무료 사용량을 아끼려면 해당 기능을 버튼 클릭 방식으로 변경하는 것이 좋습니다.

## 보안 주의사항

- API 키를 `application.properties`나 Git 저장소에 직접 커밋하지 마세요.
- 이미 키를 파일에 적거나 외부에 노출했다면 Google AI Studio에서 기존 키를 폐기하고 새 키를 발급받는 것이 안전합니다.
- 실제 배포 시 DB 비밀번호, API 키, 외부 접속 정보는 환경변수나 별도 secret 관리 도구로 분리하세요.
- 현재 개발 편의를 위해 CSRF가 비활성화되어 있습니다. 운영 배포 전에는 CSRF, 권한 정책, 입력 검증을 다시 점검해야 합니다.

## 원격 DB 프로필

`application-remote.properties`에는 기존 원격 DB 설정이 보존되어 있습니다. 필요할 때만 아래처럼 활성화합니다.

```bash
./gradlew bootRun --args='--spring.profiles.active=remote'
```

원격 DB 사용 시 접속 정보와 비밀번호는 별도로 안전하게 관리해야 합니다.
