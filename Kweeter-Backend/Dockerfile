FROM ubuntu:17.10

RUN apt-get update && \ 
	apt-get upgrade -y && \
	apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean && \
	apt-get install wget unzip -y && \
	wget https://services.gradle.org/distributions/gradle-4.5.1-bin.zip && \
	mkdir /opt/gradle && \
	unzip -d /opt/gradle gradle-4.5.1-bin.zip
ENV PATH=$PATH:/opt/gradle/gradle-4.5.1/bin

RUN gradle -v

COPY . /app
WORKDIR /app
RUN sh ./gradlew clean build --stacktrace
WORKDIR /
COPY /app/build/libs /kweeter-backend
RUN ls /kweeter-backend