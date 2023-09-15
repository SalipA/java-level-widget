# JavaRush level widget

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

### About Project
JavaRush is a popular platform for java learning. A high user profile level is a source of pride for many students. 
Of course it`s important for me too. This repository contains a backend for the widget service, which allows you to 
get a widget like this by user id.

![img.png](img.png)


### About Stack
Technologies were used:
+ Java
+ Spring Boot
+ Maven

### About Architecture
+ One main-service

### About Functionality


+ **Main-service**

    + service process the core business logic and combines the work of two main components:  
      + **WebParser** : get information about user`s level and rating
      + **CacheHandler**:
        organize data caching: saving, initializing and checking the relevance of data. The cache is stored in files 
        in a directory "cache" inside the docker-container and becomes irrelevant 1 day after the first request   
      
     

### About API

+ GET `/widget/{userId}`

userId - is user profile ID in JavaRush






### About Run
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Use Maven for packaging and just ```docker-compose up``` it! 🐳