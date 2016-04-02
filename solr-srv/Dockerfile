#
# Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
#

FROM solr
MAINTAINER docker@urchinly.uk
LABEL uk.urchinly.tech=data
EXPOSE 8983
COPY wabi_core /opt/solr/server/solr/wabi_core
USER root
RUN chown -R solr:solr /opt/solr/server/solr/wabi_core
USER solr
