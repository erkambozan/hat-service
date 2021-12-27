FROM    openjdk:13  as builder
WORKDIR /server
COPY gradlew build.gradle ./gradle /server/
COPY src /server/src
COPY gradle /server/gradle
RUN ./gradlew assemble && mv build/libs/*.jar app.jar


FROM    openjdk:13
WORKDIR /server
COPY    --from=builder /server/app.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]