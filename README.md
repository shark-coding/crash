# 컨퍼런스 신청

## 🧵 프로젝트 설명
컨퍼런스 관리 서비스로, 관리자는 컨퍼런스를 운영하고 사용자들은 컨퍼런스 신청을 할 수 있습니다.
사용자와 관리자의 역할을 JWT 기반 권한 분리를 통해 구분하여 보안을 강화했습니다. 컨퍼런스 신청 시, 관리자에게 Slack 알림이 자동으로 전송되어 실시간으로 신청 현황을 확인할 수 있습니다.
또한, 로그인 시 발급되는 인증 토큰인 Authorization, 컨퍼런스 단건 조회 및 전체 목록 조회 시에 Redis 캐시에 저장하여 데이터베이스 부하를 줄이고 응답 속도를 빠르게 하도록 구현했습니다.

## 📁 프로젝트 구조
```
project-root
├── src
│   └── main
│       ├── java
│       │   └── com.project.crash
│       │       ├── config         # 보안, 인코딩, 캐시 설정
│       │       ├── controller     # API 컨트롤러
│       │       ├── exception      # 예외 처리
│       │       ├── model          # 엔티티, DTO, 에러 모델
│       │       ├── repository     # JPA 인터페이스
│       │       ├── service        # 비즈니스 로직
│       │       └── CrashApplication # 메인 실행 파일
│       └── resources
│           ├── application.yaml   # 환경 설정
│           ├── application-dev.yaml   # dev 환경 설정
│           ├── application-prod.yaml   # prod 환경 설정
│           └── application-secret.yml   # key값
├── README.md
```

## 🧰 기술 스택
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Gradle
- Redis
- Docker
- Postman
- Slack API
- Faker
- Git, GitHub

## ✨ 주요 기능
- ✅ JWT 기반 인증 및 권한 관리 (Spring Security 연동)
- ✅ Redis를 이용한 로그인 권한 토큰 캐싱
- ✅ 단건 조회 및 전체 목록 조회 Redis 캐시 저장하여 DB 조회 최소화
- ✅ Faler 라이브러리 활용하여 테스트용 더미 데이터 자동 생성
- ✅ Slack API 연동으로 컨퍼런스 신청 시 관리자에게 실시간 알림 발송
- ✅ CRUD 기반의 컨퍼런스 신청 및 관리 기능 RESTFul API


## 🔥 주요 도전 과제 및 해결 방법
### 1. JWT + Spring Security
- 토큰 유효성 검사 및 권한 검증 처리
- 권한에 따른 API 접근 제어 설정

### 2. Slack 알림 연동 자동화
- 사용자가 컨퍼런스 신청 시, Slack Webhook API 호출하여 관리자 채널에 알림 전송
- WebClient 사용하여 비동기 처리

### 3. Redis를 활용한 캐시 관리
- 로그인 인증 후 권한 정보를 Redis에 저장하여 빠른 인증 처리
- 컨퍼런스 검색 결과 캐싱으로 DB 부하 감소 및 응답 속도 향상

### 4. Faker를 활용한 테스트 데이터 자동 생성
- 실무와 유사한 테스트 데이터 대량 생성

### 5. API 문서 자동화 (Postman)
- Postman으로 테스트한 API를 문서화
- 공개 문서로 변환 → 다른 사용자도 쉽게 사용 가능하도록 공유

## 📌 API 문서

API 명세는 Postman으로 작성하고 문서화하였습니다.  
👇 아래 링크에서 확인하실 수 있습니다:

🔗 [API 문서 보러가기](https://documenter.getpostman.com/view/29995397/2sB2xCi9V1)
