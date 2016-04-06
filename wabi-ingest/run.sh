
mvn clean install -P prod && mvn docker:build

# docker run -it -p 9081:8081 --memory 1g --rm --net=wabi_wabi-tier --name ingest urchinly/wabi-ingest

mvn spring-boot:run -Dserver.port=9081 -P prod

