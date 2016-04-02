#
# Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
#

FROM java:alpine
MAINTAINER docker@urchinly.uk
LABEL uk.urchinly.tech=SpringBoot
RUN ["mkdir", "-p", "/data/vault"]
VOLUME ["/data/vault", "/tmp"]
COPY *-svc-*.jar app.jar
RUN /bin/sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
