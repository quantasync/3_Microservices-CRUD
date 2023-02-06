#Build JAR
FROM maven:3.6.3-openjdk-17 AS BUILD_IMAGE
RUN mkdir /apps
COPY . /apps
WORKDIR /apps
RUN mvn clean
RUN mvn package

FROM openjdk:17
COPY --from=BUILD_IMAGE /apps/target/Mic1-0.0.1-SNAPSHOT.jar Mic1-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Mic1-0.0.1-SNAPSHOT.jar"]