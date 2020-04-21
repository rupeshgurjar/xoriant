Problem Statement:  

 

Implement Cache Synch for Microservice. Suppose there is a Microservice named as 'A.' As scalable architecture, there is more than one instance of A Microservice.

Data added to Cache in one instance using POST operation should be published to other instance Cache, and it should be available via GET operation


Used Spring Boot 

Use appropriate data structure ( I used ConcurrentHashMap)

Use suitable design pattern wherever applicable (Pub-Sub used based event progration)

Use in-memory H2 database. ( You can use other database as per your convenient )

Use Public Subscriber Model (Used)

Don’t use any third party in memory cache (Used ConcurrentHashMap)

Don’t use any messaging broker. ( You have to implement pub-sub model using database ) - Used Pub-Sub though Application Event Listner and 
Spring Asyc to liste/update cache of other servers.
