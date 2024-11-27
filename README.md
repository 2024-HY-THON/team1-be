![image](https://github.com/user-attachments/assets/bdcf3de8-64a0-482b-a433-8df4291069df)
### DB연동
src/main/resources/application.properties  
- create database 이름; 으로 db생성 후 3306/hyppp의 hyppp를 본인이 생성한 db이름으로 바꾸기  

- username, password 부분 본인 것으로 바꾸기

  
### api
POST /usernameVerify  
application/json

    "username"
200
409

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

POST /login  
application/x-www-form-urlencoded
또는
application/json

    "username"
    "password"

200
401

단일 jwt반환



