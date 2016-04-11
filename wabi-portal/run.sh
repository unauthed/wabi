#!/usr/bin/env bash

# docker run -it -p 9080:80 --memory 500m --rm --net=wabi_wabi-tier --name wabi-portal urchinly/wabi-portal

docker run -it -p 9080:80 -v $PWD/html:/usr/share/nginx/html --memory 500m --rm --net=wabi_wabi-tier --name wabi-portal urchinly/wabi-portal

