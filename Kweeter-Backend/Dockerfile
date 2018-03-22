FROM ubuntu:17.10

RUN apt-get update 
RUN	apt-get install -y  software-properties-common
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer
RUN	apt-get install wget unzip -y
RUN apt-get clean
RUN	wget https://services.gradle.org/distributions/gradle-4.5.1-bin.zip
RUN	mkdir /opt/gradle
RUN	unzip -d /opt/gradle gradle-4.5.1-bin.zip

ENV PATH=$PATH:/opt/gradle/gradle-4.5.1/bin
ENV DB_CONNECTION_URL=teunwillems.nl:5432
ENV DB_USERNAME=javaee
ENV DB_PASSWORD=javaeesemester6

RUN gradle -v

WORKDIR /app
COPY . .
RUN ls ./

RUN gradle war

RUN ls build/libs
RUN cp build/libs/app.war build/unpacked/dist/wildfly-11.0.0.Final/standalone/deployments/app.war
RUN ls build/unpacked/dist/wildfly-11.0.0.Final/standalone/deployments
CMD ["build/unpacked/dist/wildfly-11.0.0.Final/bin/standalone.sh"]
EXPOSE 8080 9990