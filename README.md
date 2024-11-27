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





