#
# Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
#

docker run -d -e "SPRING_PROFILES_ACTIVE=prod" -p 8088:8088 --name wabi-search -h wabi-search urchinly/wabi-search
docker exec -it search /bin/sh
