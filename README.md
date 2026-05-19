# Footmap Spring

풋살 팀과 매치를 찾고, 경기 정보를 등록하며, 커뮤니티 게시판과 공지/풋살장 정보를 함께 제공하는 Spring Boot 기반 웹 애플리케이션입니다.

## 주요 기능

- 회원 가입, 로그인, 자동 로그인, 마이페이지
- 선수 및 팀 목록 조회, 상위 선수/팀 표시
- 팀 검색, 팀 상세 조회, 팀 생성 및 팀 이미지 업로드
- 경기 등록, 경기 검색, 내 경기 내역 화면
- 게시판 목록, 검색/페이징, 글 작성, 상세 조회, 수정, 삭제
- 공지사항, 이벤트, FAQ, 사이트 소개, 사이트 준수 사항
- 풋살장 정보 및 풋살장 리스트 조회

## 기술 스택

- Java 11
- Spring Boot 2.7.6
- Spring MVC
- Spring Security
- Thymeleaf, Thymeleaf Layout Dialect
- MyBatis
- Spring Data JPA
- MySQL
- Gradle
- Lombok

## 프로젝트 구조

```text
src/main/java/footmap/footmap_spring
├── Security/        # 사용자 인증 상세 서비스와 로그인 실패 핸들러
├── config/          # Spring Security, 정적 리소스, 업로드 경로 설정
├── controller/      # 화면 요청 처리 컨트롤러
├── dao/             # MyBatis Mapper 인터페이스
├── dto/             # 화면/DB 전달 객체
└── service/         # 비즈니스 로직

src/main/resources
├── mybatis/mapper/  # MyBatis XML 매퍼
├── static/          # CSS, 기본 이미지 등 정적 파일
└── templates/       # Thymeleaf 화면 템플릿
```

## 실행 전 준비

1. Java 11을 설치합니다.
2. MySQL을 설치하고 `db_footmap` 데이터베이스를 준비합니다.
3. `src/main/resources/application.properties`에서 로컬 환경에 맞게 DB 접속 정보를 수정합니다.

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/db_footmap?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=your_username
spring.datasource.password=your_password
```

이미지 업로드 컬럼이 없는 DB를 사용하는 경우 아래 SQL을 적용합니다.

```bash
mysql -u your_username -p db_footmap < docs/image-upload-db.sql
```

## 실행 방법

macOS/Linux:

```bash
./gradlew bootRun
```

Windows:

```bat
gradlew.bat bootRun
```

실행 후 브라우저에서 다음 주소로 접속합니다.

```text
http://localhost:8080
```

## 주요 URL

| URL | 설명 |
| --- | --- |
| `/` | 메인 화면 |
| `/signUp` | 회원 가입 |
| `/login` | 로그인 |
| `/Mypage` | 마이페이지 |
| `/Team/t_search` | 팀 검색 |
| `/Team/t_commit` | 팀 생성 |
| `/game/registerForm` | 경기 등록 |
| `/game/search` | 경기 검색 |
| `/board/list` | 게시판 목록 |
| `/Page/Notice` | 공지사항 |
| `/Page/Event` | 이벤트 |
| `/Page/FAQ` | FAQ |
| `/Page/FINFO` | 풋살장 정보 |
| `/Page/FLIST` | 풋살장 리스트 |
| `/Page/s_intro` | 사이트 소개 |
| `/Page/s_rule` | 사이트 준수 사항 |

## 업로드 파일

팀 이미지 등 업로드 파일은 기본적으로 프로젝트 루트의 `uploads` 디렉터리에 저장됩니다.

```properties
app.upload.path=uploads
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=20MB
```

업로드된 파일은 `/uploads/**` URL로 제공됩니다.

## 인증/권한

Spring Security를 사용합니다. `/`, `/login`, `/signUp`, `/Write`, 정적 리소스, 업로드 리소스는 공개되어 있으며, 일부 기능은 로그인한 사용자 권한이 필요합니다. 예를 들어 경기 검색 화면은 `USER` 권한을 요구합니다.

## 테스트

```bash
./gradlew test
```

## 참고 사항

- 현재 설정 파일은 로컬 MySQL 환경을 기준으로 작성되어 있으므로 배포 환경에서는 DB 계정, 비밀번호, 업로드 경로를 별도로 관리하는 것이 좋습니다.
- `docs/image-upload-db.sql`은 이미지 경로 저장 컬럼을 추가하는 보조 SQL입니다.
- Gradle `war` 플러그인이 적용되어 있어 WAR 패키징 구성이 포함되어 있습니다.
