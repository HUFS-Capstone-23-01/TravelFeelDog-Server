
SpringBoot Application Software Architecture
---
[ Intro ]
1. 일반적인 Layerd Architecture 는 다음과 같다. (마틴파울러 DDD)

Presentation : Controller

Application : Service , Usecase , Dto mapper

Domain : Entity , VO

Infrastructure : Repository ,Dao

2. 도메인 모델 패턴을 이용한다. (엔터프라이즈 애플리케이션 아키텍처 패턴 (PEAA), 마틴 파울러)


---
[ Package Structure ]

- travelfeeldog

    - community : 웹 서비스 커뮤니티 관련 영역으로 회원들이 피드를 올리고 서로 소통합니다

    - member : 회원 관련 영역

    - place : 여행필독서 모바일 앱 서비스에서 여행지 관련 영역

    - review : 여행필독서 모바일 앱 서비스에서 여행지에 대한 회원들의 리뷰 관련 영역

    - global : 서비스 전반에 사용되는 기능 영역

    - infra
        - aws s3
        - redis
        - swagger
      
    - web : 테스트를 위한 웹페이지 관련 영역