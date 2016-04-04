#
# Copyright (C) 2016 Urchinly <wabi@urchinly.uk>
#

FROM java:alpine
MAINTAINER docker@urchinly.uk
LABEL uk.urchinly.tech=SpringBoot
LABEL uk.urchinly.tier=wabi
EXPOSE 8088
RUN ["mkdir", "-p", "/data/vault"]
VOLUME ["/data/vault", "/tmp"]
COPY wabi-*.jar app.jar
RUN /bin/sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
