#!/usr/bin/env bash

docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq -h rabbitmq urchinly/wabi-rabbitmq


