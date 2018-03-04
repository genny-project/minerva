FROM openjdk:8u131-jre-alpine
RUN apk update && apk add jq && apk add bash

ADD target/minerva-fat.jar /service.jar

ADD docker-entrypoint.sh /docker-entrypoint.sh


WORKDIR /

EXPOSE 5701
EXPOSE 8081
EXPOSE 15701

#CMD ["java"]
ENTRYPOINT [ "/docker-entrypoint.sh" ]

