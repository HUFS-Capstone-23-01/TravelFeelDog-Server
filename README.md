# 여행필독서,TravelFeelDog 🐕

리뷰 시스템 개선을 통한 반려동물 여행지 추천 서비스

---

## TEAM 우아한 공돌이 개발팀 👨‍👨‍👧‍👦

| <img src="https://github.com/HUFS-Capstone-23-01/TravelFeelDog-Server/assets/37647483/4339472e-01ff-4e50-9aed-9740be8c77ef" width="100" height="100"> | <img src="https://github.com/HUFS-Capstone-23-01/TravelFeelDog-Server/assets/37647483/09075c42-2ca2-469d-a6ab-04a7435a8260" width="100" height="100"> | <img src="https://github.com/HUFS-Capstone-23-01/TravelFeelDog-Server/assets/37647483/a1df3bb5-a916-458b-81f7-2a59bc259daf" width="100" height="100"> | <img src="https://github.com/HUFS-Capstone-23-01/TravelFeelDog-Server/assets/37647483/9949ef3d-5b2c-4aaf-a413-7d8cc8c1f3ee" width="100" height="100"> |
|:--------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------:|
| 윤승민([@Yoon-Min](https://github.com/Yoon-Min))                                                                                                    | 임지선([@Lim-JiSeon](https://github.com/Lim-JiSeon))                                                                                                | 조성현([@chosunghyun18](https://github.com/chosunghyun18))                                                                             | 최승엽([@seungyeobchoi](https://github.com/seungyeobchoi))                                                                                          |
| Team Lead / Android Mobile                                                                                                                          | Frontend / Web                                                                                                              | Back End Lead / Back End & Infra                                                                                                       | Follower / Back End                                                                                                         |



### Front-end

---

#### Mobile

| Category           | Stack   |
| ------------------ | ------- |
| Language           | Kotlin  |
| Framework(Library) | Android |

#### Web

| Category           | Stack      |
| ------------------ | ---------- |
| Language           | Javascript |
| Framework(Library) | React      |

### Back-end

---

| Category       | stack                    |
| -------------- | ------------------------ |
| Language       | Java 17                  |
| Framework      | Spring Boot 2.7.7 -> 3.1.0 |
| ORM            | JPA/Hibernate , Data JPA |
| Secure         | SpringSecurity, OAuth2 ,JWT|
| Test           | JUnit 5                  |
| Database       | MySQL 8.0.               |
| Build          | Gradle 7.5               |
| Infra          | AWS ,Nginx , Redis       |
| Third Part API | OpenAi text-davinci-003  |
| etc            | Ubuntu 22 ,t4g.small(arm64)|

### Communication

| Category      | Stack       |
| ------------- | ----------- |
| Communication | google Meet |
| Documents     | Notion      |

---

### 프로젝트 목표 & 기능

모바일

- 사용자들의 리뷰 기반, 조회수 기반 등의 방식의 다양한 방식의 장소 추천
- 다양한 제시된 키워드를 선택 하고 리뷰를 작성하여 레벨을 올리기
- 다양한 필터 및 검색 키워드 등을 사용하여 리뷰들을 확인하고 검색
- Chat Gpt 를 활용한 장소 검색 및 안내 기능

웹

- 반려견주들 간의 네트워크 형성을 위한 반려동물 케어 서비스 제공
- 반려동물에 대한 정보 제공 및 정보 교류 가능
- 커뮤니티 서비스를 통한 소통의 장 제공
- 커뮤니케이션을 통한 반려동물 시장 활성화

### ERD Table (https://www.erdcloud.com/d/kypw5wju6a9c3rq2g)

![TravelFeelDog](https://github.com/HUFS-Capstone-23-01/TravelFeelDog-Server/assets/37647483/903242e9-fdfa-4aed-ad5f-758b340e5040)

### 브랜치 관리 전략

- Git Flow를 사용하여 브랜치를 관리합니다.

- Release,Develop 브랜치는 Pull Request 후 merge를 진행합니다.

- 메인 브렌치인 Develop인 경우 리뷰와 PR 을 필수로 하는 깃 protrction이 설정되어 있습니다.

![branchImage](https://user-images.githubusercontent.com/37647483/226156092-df21a222-76c4-41d0-a9f7-46112ae00ce0.jpg)

- Release : 배포시 사용합니다.
- Develop : 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
- Feature : 기능 개발을 진행할 때 사용합니다.
- Hot-Fix : 배포를 진행한 후 발생한 버그를 수정해야 할 때 사용합니다.
- Main : v1.0.0 , v1.1.0 과 같이 2번째 자리수의 버전 까지를 저장합니다.
  <br><br>
  <b>브랜치 관리 전략 참고 문헌</b><br>
- [우아한 형제들 기술 블로그](http://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)
- [Bitbucket Gitflow Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)
