#!/usr/bin/env bash
#
# Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
#


echo remove containers
docker rm $(docker ps -q -a)

echo remove images
docker rmi $(docker images -q -a)

echo remove volumes
docker volume rm $(docker volume ls -q -f dangling=true)


