FROM timothygillespie/timewarrior-maven-alpine:1.4.3

COPY src /home/app/src
COPY pom.xml /home/app
COPY .github/twjc.ci.properties /home/app/twjc.properties

RUN mvn -f /home/app/pom.xml clean compile test

