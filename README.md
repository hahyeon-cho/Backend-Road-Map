# ️

# ✏ README

## ◾목차

- [개요](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)
- [UseCase Diagram](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)
- [Flow Chart](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)
- [Main Function](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)

- [시스템 구성도](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)
- [Figma](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)
- [ERD](https://www.notion.so/README-a31b4946f63f4c8a84152c55274b0b47?pvs=21)

---

## ◾개요

- 프로젝트 이름: NCNK (No Coding No Kakao)
- 프로젝트 기간 : 2023.02. ~ 2023.04.01
- 팀원 : **이재현, 김효선, 박하영, 조하현, 현세미**

---

## ◾시스템 구성도

![SystemArchitecture](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/SystemArchitecture.png)

**기술 스택**

- **Front-End** : HTML, CSS, Java Script
- **Back-End** : Java, Gradle, Spring Framework, JPA
- **DB** : Amazon RDS (MySQL)
- **Health Check** : Promethus
- **Monitoring Tool** : Grapana
- **API**
    - OAuth 로그인 : Google, Naver
    - 웹 컴파일러 : Web Compiler API
    - 추천 도서 목록 : Aladin API
    - 코딩 테스트 문제 : LeetCode API

---

## ◾UseCase Diagram

![UseCaseDiagram](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/UseCaseDiagram.png)

---

## ◾Figma

![Figma](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/Figma.png)

---

## ◾Flow Chart

![FlowChart](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/FlowChart.png)

---

## ◾ERD

![ERD](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/ERD.png)

---

## ◾Function 1 : **RoadMap page**

**백엔드 공부 주제, 순서 등을 소개하는 로드맵**

![Func1](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/Func1.png)

![Func1-1](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/Func1-1.png)

- ‘대분류 카테고리에 속하는 소분류 카테고리`의 주제 및 설명
- 카테고리에 알맞은 책 추천 (Aladin API)
- 퀴즈 문제를 맞추면 다음 대분류 카테고리 접속 가능

## ◾Function 2 : **Practice page**

![Func2](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/Func2.png)

**언어별 코드 연습 기능**

- Java, JavaScript, Python 언어를 자유롭게 입력하여 출력 테스트 기능
- DownLoad 기능을 통해 사용자가 입력한 코드 저장 가능
- Import 기능을 통해 저장한 코드를 불러오기 가능

## ◾Function 3 : **Coding Test page**

![Func3](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/Func3.png)

![Func3-1](/Users/jaehyun/Documents/IdeaProjects/KaKaoCloud/Project/Second/BackEndRoadMap/readmeImg/Func3-1.png)

**코딩 테스트 문제 가능**

- 알고리즘 문제를 풀 수 있는 페이지
- 리트코드 API를 이용해 문제를 크롤링하여 직접 문제를 풀 수 있음