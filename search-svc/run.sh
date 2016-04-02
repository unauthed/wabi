#
# Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
#

docker run -d -e "SPRING_PROFILES_ACTIVE=prod" -p 8088:8088 --name search -h search urchinly/search-svc
docker exec -it search /bin/sh


