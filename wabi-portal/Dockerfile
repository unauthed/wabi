#
# Copyright (C) 2016 Urchinly <wabi-sabi@urchinly.uk>
#

FROM nginx:alpine
MAINTAINER docker@urchinly.uk
LABEL uk.urchinly.tech=web
LABEL uk.urchinly.tier=wabi
RUN ["mkdir", "-p", "/data/vault"]
VOLUME ["/data/vault", "/tmp"]
EXPOSE 80
EXPOSE 443
COPY html/ /usr/share/nginx/html

