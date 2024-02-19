# 3 Microservices
### This is practice project that was created in my educational purposes to test some technologies.

Each miroservice works with each user field accordingly. Mic1 (firstname), Mic2 (middlenae), Mic3(lastname).

NOTE Mic1 open to client, and Mic2 and Mic3 are closed, to send requests to them we use server-to-server connection.

Mic1 uses FeignClient to speak with Mic2, and Mic2 uses WebClient to speak with Mic3.

Mic1 accepts CRUD requests from client and works with firstname, also sends CRUD requests to Mic2 to perform some operations with middlename and than Mic2 sends CRUD requests to Mic3 to perform some operations with lastname.

Also we use MongoDB as our database to create, retrieve, update, delete user fields of user.
