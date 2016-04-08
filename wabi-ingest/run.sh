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


mvn clean install -P prod && mvn docker:build

# docker run -it -p 9081:8081 --memory 1g --rm --net=wabi_wabi-tier --name ingest urchinly/wabi-ingest

mvn spring-boot:run -Dserver.port=9081 -P prod

