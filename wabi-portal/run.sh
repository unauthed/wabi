#!/usr/bin/env bash

docker run -it -p 9080:80 -v $PWD/html:/usr/share/nginx/html --memory 500m --rm --net=wabi_sabi-tier --name wabi-portal urchinly/wabi-portal

