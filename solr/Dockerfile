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

FROM solr
MAINTAINER docker@urchinly.uk
LABEL uk.urchinly.tech=data
EXPOSE 8983
COPY wabi_core /opt/solr/server/solr/wabi_core
USER root
RUN chown -R solr:solr /opt/solr/server/solr/wabi_core
USER solr
