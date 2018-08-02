# sikolah
Simple Login, Register and CRUD system.
Sistem Informasi Sekolah Using Spring Boot.


------------
Technology:
- Springboot
- Hibernate JPA
- thymeleaf
- MySql
- Spring Security
------------
Features:
- spring security (register, login)
	- offline
	- online
	- oAuth (***not yet***)
- simple pagination (***options to show how many data loaded in 1 page still not working.. idk why.***)
- Crud (***DONE but still, I try to add modals pop-up in thymeleaf***)
- @Rest controller (***Not Yet***)


## how to run:
in your Command line type: `mvn spring-boot:run`  
or  
run sikolahApplication from your favorite IDE  

> database will be created automatically

### login
3 roles

user: admin  
pass: admin  

user: guru  
pass: guru  

user: siswa  
pass: siswa  

> see setupDataLoader
