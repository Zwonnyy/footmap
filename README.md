# FootMap

풋살 팀, 선수, 구장, 경기 매칭, 커뮤니티를 한 곳에서 관리하는 Spring Boot 기반 풋살 매칭 플랫폼입니다.

FootMap은 풋살 유저가 상대팀을 찾고, 구장 정보를 확인하고, 팀/선수 정보를 관리하는 과정을 하나의 서비스 흐름으로 연결하는 것을 목표로 만들었습니다. 단순 게시판형 사이트가 아니라 `팀 생성 -> 경기 등록 -> 상대팀 신청 -> 승인/거절 -> 경기 내역 확인`까지 이어지는 매칭 플로우를 구현했습니다.

## 프로젝트 목표

풋살 경기를 준비할 때 사용자는 보통 팀원 관리, 상대팀 모집, 구장 정보 확인, 커뮤니티 소통을 각각 다른 채널에서 처리합니다. 이 프로젝트는 그 과정을 하나의 웹 서비스 안에서 처리할 수 있도록 구성했습니다.

- 팀과 선수를 서비스 안에서 관리
- 풋살장 위치, 비용, 운영시간 등 의사결정에 필요한 정보 제공
- 경기 등록과 신청, 승인/거절까지 이어지는 매칭 흐름 구현
- 커뮤니티 게시판을 통해 경기 모집과 소통 지원
- Gemini AI를 활용해 소개글 작성, 게시글 요약, 경기 후기 작성 등 반복적인 글쓰기 작업 보조

## 핵심 구현

### 1. 경기 매칭 플로우

경기 등록팀이 일정, 구장, 인원수를 입력해 경기를 등록하면 다른 팀이 해당 경기에 신청할 수 있습니다. 등록팀은 신청 상태를 확인하고 승인 또는 거절할 수 있으며, 사용자는 내 경기 내역에서 진행 상태를 확인할 수 있습니다.

이 기능을 통해 단순 목록 조회가 아니라 실제 풋살 매칭 서비스에 필요한 상태 흐름을 구현했습니다.

### 2. 팀/선수 관리

사용자는 팀을 생성하거나 기존 팀에 가입할 수 있습니다. 팀 상세 페이지에서는 팀 소개, 홈 구장, 승/무/패 정보와 팀원 스탯을 확인할 수 있습니다. 선수 상세 페이지와 랭킹 페이지에서는 골, 어시스트, 수비 지표를 기반으로 개인 스탯을 보여줍니다.

### 3. 풋살장 상세 정보와 지도 연결

풋살장 목록뿐 아니라 상세 페이지를 별도로 구현했습니다. 상세 페이지에는 주소, 구장 비용, 운영 시간, 주차, 샤워실, 이용 안내를 표시하고, 주소 기반으로 Naver/Kakao/Google 지도 앱 연결을 제공합니다.

### 4. 권한 기반 게시판

커뮤니티 게시판은 로그인 사용자만 작성할 수 있고, 게시글 수정/삭제는 작성자만 가능하도록 제한했습니다. 기존 게시글 상세 조회에서 중복 join 결과로 발생하던 `TooManyResultsException` 문제도 SQL 조회 구조를 수정해 해결했습니다.

### 5. Gemini AI 기능

Gemini API를 연동해 서비스 곳곳에서 사용자의 글쓰기와 정보 해석을 보조합니다. API 키가 없는 환경에서도 서비스가 깨지지 않도록 fallback 문구를 제공하도록 구현했습니다.

AI가 사용되는 위치:

- 게시글 작성: AI 요약 미리보기, 표현 검사
- 게시글 상세: 게시글 요약, 표현 검사 결과 표시
- 경기 등록: 경기 소개글 생성
- 팀 생성: 팀 소개글 생성
- 선수 상세: 선수 스탯 기반 분석
- 팀 상세: 팀 추천 코멘트
- 게임 찾기: 매칭 추천
- 내 경기 내역: AI 경기 후기 생성

## 기술 스택

| 영역 | 기술 |
| --- | --- |
| Language | Java 11 |
| Backend | Spring Boot 2.7.6 |
| Security | Spring Security |
| View | Thymeleaf, Thymeleaf Layout Dialect |
| Database | MySQL |
| Persistence | MyBatis, Spring Data JPA |
| Build | Gradle |
| AI | Gemini API |

## 기술적 포인트

- MVC 구조를 기반으로 Controller, Service, Mapper, DTO 계층을 분리했습니다.
- MyBatis XML Mapper를 사용해 주요 SQL을 명시적으로 관리했습니다.
- Spring Security로 로그인, 접근 권한, 작성자 검증을 처리했습니다.
- `schema.sql`, `data.sql`을 통해 로컬 실행 시 기본 테이블과 샘플 데이터를 자동 구성했습니다.
- Gemini API 키는 코드에 직접 저장하지 않고 환경변수 기반으로 주입하도록 구성했습니다.
- 외부 AI API 호출 실패 또는 키 미설정 상황에서도 fallback 응답으로 화면이 정상 동작하도록 처리했습니다.

## 주요 화면

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

## ERD 요약

주요 테이블은 다음 역할을 가집니다.

| 테이블 | 역할 |
| --- | --- |
| `USER` | 회원, 선수 스탯, 권한 정보 |
| `TEAM` | 팀 정보, 홈 구장, 전적 |
| `TEAM_MANAGEMENT` | 회원과 팀의 소속 관계 |
| `FINFO` | 풋살장 정보 |
| `GAMELIST` | 경기 등록, 신청팀, 매칭 상태 |
| `BOARD` | 커뮤니티 게시글 |
| `persistent_logins` | remember-me 로그인 토큰 |

## 실행 방법

### 1. MySQL 데이터베이스 생성

```sql
CREATE DATABASE db_footmap DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

기본 DB 설정은 [application.properties](src/main/resources/application.properties)에 있습니다.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_footmap?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=
```

앱 실행 시 `schema.sql`, `data.sql`이 실행되어 기본 테이블과 샘플 데이터가 생성됩니다.

### 2. Gemini API 키 설정

AI 기능을 실제 API로 사용하려면 환경변수에 Gemini API 키를 설정합니다.

```bash
export GEMINI_API_KEY=발급받은_키
```

기본 모델은 무료 티어에 맞춰 `gemini-3.1-flash-lite`로 설정했습니다.

```properties
gemini.model=${GEMINI_MODEL:gemini-3.1-flash-lite}
```

다른 모델을 사용하려면 실행 환경에서 변경할 수 있습니다.

```bash
export GEMINI_MODEL=gemini-3.5-flash
```

`GEMINI_API_KEY`가 없으면 외부 API를 호출하지 않고 로컬 fallback 문구로 동작합니다.

### 3. 애플리케이션 실행

```bash
./gradlew bootRun
```

기본 주소:

```text
http://localhost:8080/
```

8080 포트가 사용 중이면 다른 포트로 실행합니다.

```bash
./gradlew bootRun --args='--server.port=8081'
```

### 4. 테스트

```bash
./gradlew test
```

## 보안 및 운영 고려사항

- API 키, DB 비밀번호는 Git에 커밋하지 않고 환경변수로 관리합니다.
- 이미 노출된 API 키는 폐기 후 재발급하는 것이 안전합니다.
- 현재 개발 편의를 위해 CSRF가 비활성화되어 있습니다. 운영 배포 전 CSRF 정책을 다시 활성화하거나 대체 방어책을 적용해야 합니다.
- AI 자동 표시 화면은 페이지 진입 시 API 호출이 발생할 수 있으므로, 운영 환경에서는 버튼 클릭 방식 또는 캐싱 적용을 고려할 수 있습니다.

## 개선 가능성

- 경기 신청 시 팀 코드 직접 입력 대신 내 팀 목록 선택 방식으로 개선
- AI 자동 호출 기능에 캐싱 적용
- 관리자 페이지를 통한 구장/게시글/회원 관리
- 경기 완료 후 실제 스코어 기록 및 선수 스탯 자동 반영
- REST API 분리 및 프론트엔드 SPA 전환
- 배포 환경에서 secret 관리, 로깅, 예외 처리 고도화

## 원격 DB 프로필

`application-remote.properties`에는 기존 원격 DB 설정이 보존되어 있습니다. 필요할 때만 아래처럼 활성화합니다.

```bash
./gradlew bootRun --args='--spring.profiles.active=remote'
```

원격 DB 사용 시 접속 정보와 비밀번호는 별도로 안전하게 관리해야 합니다.
