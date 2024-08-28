FROM docker.io/library/maven:3.9.4-eclipse-temurin-17-alpine AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests

FROM docker.io/library/eclipse-temurin:17-jdk-alpine

COPY src ./src
RUN ls -alR src
RUN apk --no-cache --update-cache add gcc gfortran python3 python3-dev py-pip build-base wget freetype-dev libpng-dev openblas-dev

RUN apk add python3 && \
    python3 -m ensurepip
RUN pip3 install numpy==1.25.2 matplotlib==3.2.1 python-dotenv psycopg2-binary

ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
