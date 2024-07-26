[![CI/CD](https://github.com/TaskWeaver/backend/actions/workflows/github-actions.yml/badge.svg?event=push)](https://github.com/TaskWeaver/backend/actions/workflows/github-actions.yml)
# 🌳 TaskWeaver
![image](https://github.com/TaskWeaver/backend/assets/111855256/931aa697-08e7-49f8-9689-619cc257ea83)

	               팀 생성부터 팀원 관리, 프로젝트의 모든 과정을 함께하는 애플리케이션 “Task Weaver”입니다.

<br>

## 주요 기능
#### ✔️ 언제 어디서든 관리할 수 있는 프로젝트
  - 휴대폰으로 언제 어디서든 프로젝트를 관리할 수 있습니다.
#### ✔️ 타임라인에서의 태스크 관리
  - 타임라인에서 프로젝트의 전체 흐름을 한눈에 파악할 수 있습니다.
  - 각 태스크마다 누가 담당하는지, 어느정도 진행되었는지 확인이 가능합니다.
#### ✔️ 오늘까지 내가 해야하는 부분을 한눈에 확인  
  - 투두에서 오늘까지 마감인 프로젝트 태스크를 각 프로젝트 별로 한눈에 확인이 가능합니다.
#### ✔️ 프로젝트 세부 사항 별 서로 리뷰 작성 가능
  - 각 Task 별로 코멘트를 남길 수 있습니다.

<br> 

# 🛠️ Tech Stack
### [BackEnd]
- Java 17 
- Spring Boot 3.0.2
- Spring Data JPA
- Spring Security
- OAuth 2.0

### [Test Tool]
- JUint5
- AssertJ
- Mockito
- JMeter

### [Build Tool]
- Gradle

### [Storage]
- MySQL 8.0
- redis 버전

### [Infra & CI-CD]
- Github Actions
- Docker
- Docker Compose
- AWS RDS
- AWS S3
- AWS EC2(ubuntu)

### [Document]
- Swagger 2.0
<br>

# 🔨 Architecture
![KakaoTalk_20240701_160324133](https://github.com/TaskWeaver/backend/assets/111855256/4dd75e96-3083-404b-9f54-c5f761b37a59)

<br>

# 📄 API 명세서 

***Swagger*** :  http://ec2-3-34-95-39.ap-northeast-2.compute.amazonaws.com:8083/swagger-ui/index.html#/

![KakaoTalk_20240701_161946579](https://github.com/TaskWeaver/backend/assets/111855256/f4c8ccb2-9a56-4331-ac90-99fa96b06e01)

<br>

# 📌 ERD
![TaskWeaver (1)](https://github.com/TaskWeaver/backend/assets/111855256/f85c7444-8e77-433a-8edf-3ea064b0f6ab)

<br>

# 💡 Git Flow 전략 - GitLab Flow
![image](https://github.com/TaskWeaver/backend/assets/111855256/557ce805-46a2-40fe-8b0a-481dae2d765e)

<br>

# 📜 Git  Commit Convention
	#이슈 번호 type: Subject
- feat        : 기능 (새로운 기능)
- fix         : 버그 (버그 수정)
- refactor    : 리팩토링
- design      : CSS 등 사용자 UI 디자인 변경
- comment     : 필요한 주석 추가 및 변경
- style       : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
- docs        : 문서 수정 (문서 추가, 수정, 삭제, README)
- test        : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
- chore       : 기타 변경사항 (빌드 스크립트 수정, assets, 패키지 매니저 등)
- init        : 초기 생성
- rename      : 파일 혹은 폴더명을 수정하거나 옮기는 작업만 한 경우
- remove      : 파일을 삭제하는 작업만 수행한 경우
<br>

# 📂 Foldering






