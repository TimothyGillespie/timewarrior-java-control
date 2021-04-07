FROM alpine AS build-timewarrior

RUN apk update
RUN apk add \
    cmake \
    make \
    g++ \
    git

RUN mkdir -p /root/code/
WORKDIR /root/code/

RUN git clone --recurse-submodules https://github.com/GothenburgBitFactory/timewarrior timewarrior
WORKDIR /root/code/timewarrior
RUN ["git", "checkout", "v1.4.2"]
RUN git clean -dfx
RUN git submodule init
RUN git submodule update

RUN cmake -DCMAKE_BUILD_TYPE=release .
RUN make
RUN make install

# Compiling server jar
FROM maven:3.6.3-openjdk-8-slim
COPY --from=build-timewarrior /usr/local/bin/timew /usr/local/bin
WORKDIR /

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean compile

ENTRYPOINT mvn -f /home/app/pom.xml test