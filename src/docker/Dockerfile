FROM centos

RUN yum install -y java

VOLUME /tmp
ADD maven/trading_platform-1.1.0.jar forexApp.jar
RUN sh -c 'touch /forexApp.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/forexApp.jar"]