
# user-feacture

4일간의 개발과정을 완성된 사용자기능 프로젝트입니다..
일반사용자랑 관리자유저가 있으며,
* 관리자 api는 관리자유저만 사용가능
* 나의정보는 로그인상태인 유저만 사용가능합니다
* 요구사항에 추가로 팍팍넣었습니다.
**메일만발송되고, SMS발송안됨!**

![0005](https://user-images.githubusercontent.com/53999997/188876090-b882dca2-86d3-489f-8911-bb239f87b420.jpg)

## Feature
* 로그인
  - 로그인성공시 accessToken 발급
* 회원가입
  - 내부적으로 핸드폰, 전화번호 중복체크 및 인증확인여부 체크 후 등록
* 회원가입 핸드폰, 전화번호 발송 및 인증
* 회원가입 핸드폰, 전화번호 중복확인
* 나의 정보 조회
  - 본인정보만 확인가능 토큰전달시
* 나의 정보 수정
  - 본인정보만 수정가능 토큰전달시
  - 이메일, 핸드폰 변경시 인증여부 및 중복여부확인
* 나의 비밀번호 변경
* 나의정보 핸드폰, 전화번호 발송 및 인증
* 나의정보 핸드폰, 전화번호 중복확인
  - 본인 핸드폰, 전화번호 아닐시에만 중복체크
* 아이디 찾기 이메일, 전화번호 발송 및 인증
  - 발송후 인증번호, 인증이메일 또는 전화번호 받아서 아이디 조회
* 비밀번호 찾기 이메일, 전화번호 발송 및 인증
  - 발송후 인증번호, 인증이메일 또는 전화번호 받아서 인증키값 조회
* 비밀번호 찾기 후 비밀번호 재설정
  - 인증키 + 인증시 이메일 또는 전화번호 + 패스워드 + 유저아이디 등으로 변경가능
* 관리자 사용자 목록 및 단일 조회
* 관리자 핸드폰, 전화번호 중복확인
* 관리자 핸드폰, 전화번호 발송 및 인증
* 관리자 사용자 생성, 수정, 삭제
  - 관리자권한유저만 사용가능


## Skill
* Java11 : lts버전인 11버전 선택 등
* H2 : 인메모리 db사용
* Swagger : apidocs 필요로 인해 추가
* JPA : 도메인 성향및 flyway처럼 ddl 자동생성
* Hibernate - jpa 프레임워크
* Querydsl - 동적쿼리 타입세이프를 위해 사용
* Jwt - 무상태성 값내장형 토큰 사용
* Spring Data Jpa - jpql 쿼리 지원
* Spring Security - 보안적인 업그레이드로 
* Spring Boot
* Java Mail - 이메일인증시 필요
* Bean Validation - api 유효성
* Lombok - 보일러플레이드코드 제너레이터기능을 위해사용
* Jackson
* Heroku - 간단한 원격배포

## 주의사항
* 전화번호는 발송안됩니다, 이메일은 실제로 발송되고있습니다
* 기본적으로 들어간 계정정보입니다.
  관리자계정 - 아이디: 2, 비번: admin
  일반계정 - 아이디: 1, 비번: user

## ERD

### USERS
USER_ID: 사용자아이디
PASSWORD: 패스워드
NICKNAME: 닉네임
NAME: 이름
EMAIL: 이메일
PHONE_NUMBER: 핸드폰번호
ROLE_TYPE: 역할유형
ENABLED: 활성여부
CREATED_AT: 등록일시
CREATED_BY: 등록자
UPDATED_AT: 갱신일시
UPDATED_BY: 갱신자

### VERIFY
VERIFY_ID: 인증아이디
VERIFY_USAGE: 인증용도
VERIFY_TYPE: 인증유형
VERIFY_TYPE_VALUE: 인증유형값
VERIFY_NUMBER: 인증번호
VERIFIED: 인증여부
CREATED_AT: 등록일시
UPDATED_AT: 갱신일시

## How To Run?
![0001](https://user-images.githubusercontent.com/53999997/188875861-2c5ca44d-03a2-43cc-a58d-c6274836b8e7.jpg)
1. gradle 빌드
![0002](https://user-images.githubusercontent.com/53999997/188875980-036fd39e-43ba-4400-aae2-1fef12758119.jpg)
2. UserFeatureApplication 실행 (.run 폴더 밑에 실행설정 파일로 되어있어서 intellij 사용시 사용가능합니다)
![0003](https://user-images.githubusercontent.com/53999997/188876015-39ef11b4-4e4b-46f2-9993-56ca0706d893.jpg)
3. http://localhost:9000 이동시 자동 swagger 페이지 이동
![0004](https://user-images.githubusercontent.com/53999997/188876073-9598c68d-9731-4ae7-90c4-0f08abb744d8.jpg)



## Interesting code
* TODO 주석을 사용하여 보여주고 싶은 코드 노출 
(intellij 사용시 TODO만 별도 조회 아니면 전체검색으로 하시면될듯싶습니다)
  
* jwt 설정
* 이메일, 전화번호에 따른 전략패턴 도입
등등 

## LOCAL URL
http://localhost:9000/swagger-ui/index.html
## HEROKU URL
https://user-features.herokuapp.com/swagger-ui/index.html