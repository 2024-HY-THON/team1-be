![image](https://github.com/user-attachments/assets/bdcf3de8-64a0-482b-a433-8df4291069df)
## DB연동
src/main/resources/application.properties
- dbms:mysql  
- create database 이름; 으로 db만 생성해준 뒤 3306/hyppp의 hyppp를 본인이 생성한 db이름으로 바꾸기 
- username, password 부분 본인 것으로 바꾸기
- 이후 서버 실행하면 테이블은 자동으로 생성
  
## api
localhost:8080
### 아이디중복확인
POST /usernameVerify  
```
application/json
{
    "username":"wlsbum"
}
```
200
409

### 이메일 인증(전송)
POST /mail/send  
application/json
```
{
    "email":"aaa@aaa.com"
}
```
200
요청 후 응답 및 메일도착까지 20초 정도 걸림  
이전 메일 전송이후 3분이 지나지않았다면, 200을 응답하나 추가 메일은 전송하지 않음.  
400(email값이 없거나 등)

### 이메일 인증(검증)
POST /mail/verify  
application/json
```
{
    "email":"aaa@aaa.com",
    "code" : 1234
}
```
code는 1000~9999사이의 네자리 값  
200 인증성공  
400(인증번호 틀렸거나 등)

### 회원가입
POST /register  
application/json
```
{
    "username": "아이디",
    "password": "pw",
    "passwordVerify": "pw"
    "name": "이름(별명)"
    "email": "aaa@aaa.com"
}
```
200  
409 이미 있는 아이디(한번 더 체크함)
400

### 로그인
POST /login  
application/x-www-form-urlencoded
또는
application/json
```
{
    "username":"아이디",
    "password":"pw"
}
```
200  
401   
단일 jwt반환



### 현재 정보(나무레벨/아이템id/이름/경험치/음악정보)
GET /tree/info

응답예시
```
{
    "level": 1,    //나무레벨 (1~4)
    "wear": 0,     //아이템id (기본0)
    "name": "범",  //이름
    "exp": 0,      //경험치 (나무에 작성한일기수)
    
    "title":"노래제목",
    "artist":"가수이름",
    "music_id": 1~6
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
최초 트리는 회원가입 시 생성  
지금은 30개인 경우 저장요청하면 무시됨.


### 월 별 emotion리스트
GET /emotions/{year}/{month}
ex. /emotions/2024/11
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

### 오늘 일기 작성 여부
GET /diary/check
응답예시  
```
0 (오늘 작성안했음)

1 (오늘 작성했음)
```

### 오늘 작성한 일기
GET /diary/today  

응답예시  
200
```
{
    "emotion": "good",
    "type": "happy",
    "content": "ddd"
}
```
404
```
Diary not found
```

### 아이템 장착
GET /wear/{num}  
ex. /wear/3   => 3번장착


### 이름및주소정보
GET/user/details

응답예시
200
```
{
  "name": "John Doe",
  "address": "1234 Elm Street, Springfield, IL"
}
```
400
```
{
  "message": "user not found"
}
```

##이름변경  
PATCH /mypage/updateName  
```
{
"newName":"새이름"
}
```

### 비밀번호 변경
Patch/mypage/updatePassword
로그인된 사용자만 접근 가능

요청 본문 예시
```
{
  "oldPassword": "oldPassword123",
  "newPassword": "newPassword456"
}
```

200
```
{
  "status": "success",
  "message": "Password updated successfully"
}
```
400
```
{
  "status": "error",
  "message": "Failed to update password"
}
```

### 주소설정
Patch /mypage/updateAddress
로그인된 사용자만 접근 가능

요청 본문 예시
```
{
  "newAddress": "서울시 강남구 역삼동 123-45"
}
```

200
```
{
  "status": "success",
  "message": "Address updated successfully"
}
```

400
```
{
  "status": "error",
  "message": "Failed to update address"
}
```


### 테스트 일기생성(2024/11/1~28까지 28개 생성)
GET /test


### 음악 설정
POST /music/set
```
{
    "title":"노래제목",
    "artist":"가수이름",
    "music_id": 2
}
```



