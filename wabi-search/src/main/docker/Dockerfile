#
# Wabi-Sabi DAM solution
# Open source Digital Asset Management platform of great simplicity and beauty.
# Copyright (C) 2016 Urchinly <wabi-sabi@urchinly.uk>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

FROM java:alpine
MAINTAINER docker@urchinly.uk
LABEL uk.urchinly.tech=SpringBoot
LABEL uk.urchinly.tier=wabi
RUN ["mkdir", "-p", "/data/vault"]
VOLUME ["/data/vault", "/tmp"]
EXPOSE 8088
COPY wabi-*.jar app.jar
RUN /bin/sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
