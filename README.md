# Back-End

# 👉 프로젝트 소개
'내가 보는 나'와 '타인이 보는 나'는 어떤 요소에서 다를까?  
타인의 시선으로 퍼스널 브랜딩을 개선하자!

<br>

## 📦 Database
![image](https://github.com/tell-me-team/Back-End/assets/70616657/a9e2d295-957e-4e11-b674-7457fa08c04e)


<br>

## 📁 Project Structure
```
📂 src
 └── 📂 main         
      ├── 📂 java          			
      |     └── 📂 com           		
      |          └── 📂 tellme        	
      |               └── 📂 tellme
      |                    ├── 📂 commons  	
      |                    |    ├── 📂 auth
      |                    |    ├── 📂 config
      |                    |    ├── 📂 constant
      |                    |    ├── 📂 enums
      |                    |    ├── 📂 exception 
      |                    |    ├── 📂 response
      |                    ├── 📂 domain # 도메인 별로 패키지 분리
      |                    |    ├── 📂 auth
      |                    |    ├── 📂 survey
      |                    |    └── 📂 user
      |                    |         ├── 📂 application # Service
      |                    |         ├── 📂 entitiy # Entity
      |                    |         ├── 📂 persistence # Repository
      |                    |         └── 📂 presentation # Controller, DTO
      ├                    └── 📄 WordiApplication.java
      └── 📂 resources
           └── 📄 applicaiton.yml


```
