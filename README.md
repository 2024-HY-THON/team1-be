![image](https://github.com/user-attachments/assets/bdcf3de8-64a0-482b-a433-8df4291069df)
## DB연동
src/main/resources/application.properties
- dbms:mysql  
- create database 이름; 으로 db만 생성해준 뒤 3306/hyppp의 hyppp를 본인이 생성한 db이름으로 바꾸기 
- username, password 부분 본인 것으로 바꾸기
- 이후 서버 실행하면 테이블은 자동으로 생성
  
## api

### 아이디중복확인
POST /usernameVerify  
application/json

    "username"
200
409

### 회원가입
POST /register  
application/json

    "username"
    "password"
    "passwordVerify"
    "name"
    "email"

200
409
400  

### 로그인
POST /login  
application/x-www-form-urlencoded
또는
application/json

    "username"
    "password"

200
401  
단일 jwt반환



### 현재 트리의 경험치(트리에 작성된 일기 수)
GET /tree/info

응답예시
```
{
"exp": 0
}
```

### 일기저장
POST /diary/save  

#### emotion
![img.png](img.png)  
왼쪽부터 "good" "soso" "bad"  
프론트에서 정해도 되고, 저장한 값 그대로 나중에 반환되니 요청시 통일만 해주시면 됩니다.

#### type
![img_1.png](img_1.png)  
왼쪽부터 "happy" "worry"  
```
{
    "emotion":"good",
    "type":"happy",
    "content":"일기 내용입니다"
}
```
200 저장됨  
400 (이미 exp30인 경우 또는 기타오류)


### 새로운 트리 생성
필요한 경우 구현하겠습니다.  
지금은 30개인 경우 저장요청하면 무시됨.


### 이번달 emotion리스트
GET /emotions  
응답예시(날짜순)
```
[
    {
        "day": 3
        "emotion": "good"
    },
    {
        "day": 6
        "emotion": "good"
    },
    {
        "day": 17
        "emotion": "good"
    }
]
```
이번달 작성x시 예시
```
[]
```

## 개발예정  
### 이름변경  
### 비밀번호 변경
### 주소설정