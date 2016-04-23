#!/usr/bin/env bash
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

docker run -it -p 8888:80 -p 443:443 --memory 500m --rm --net=wabi_sabi-tier --name wabi-haproxy urchinly/wabi-haproxy
