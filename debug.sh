export ES_JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 
mvn package -DskipTests
pkill java
ps -ef|grep elastic*
tar xvf ./target/releases/elasticsearch-*.tar.gz
elasticsearch-*/bin/elasticsearch -f
