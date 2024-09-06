<h1 align="center">FOSS</h1>
<p align="center"><img align="center" width=350 height=300 src="https://github.com/user-attachments/assets/c3cea430-3564-42da-8d98-d8936ce6589e"> </p>

<p align="center">FC ONLINE의 전적 조회 서비스⚽</p>

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

# 구현

## [서버](https://github.com/fc-online-stats-searching/foss/tree/server)

**기술 스택**
- Spring Boot
- Spring MVC
- ORM(Spring Data JPA)
- MySQL

## [안드로이드](https://github.com/fc-online-stats-searching/foss/tree/android)

**기술 스택**
- MVVM
- Jetpack Compose
- AAC ViewModel
- Repository 패턴
- UI State
- Retrofit
- MockK
- Hilt
- Woogi DI(직접 개발한 의존성 주입 라이브러리)

**[Woogi DI](https://github.com/boogi-woogi/woogi-di)**
- 직접 개발한 의존성 주입 라이브러리
- Kotlin의 Reflection을 기반으로 런타임 의존성 주입
- [적용 코드 보기](https://github.com/fc-online-stats-searching/foss/tree/oldversion)
- 현재는 Hilt로 migration한 상태
  
**테스트**
- ViewModel Test
- Domain Test
