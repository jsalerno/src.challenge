mvn clean install -Dmaven.test.skip=true
cd browse
mvn spring-boot:run &
cd ../manage
mvn clean install
ps -ef | grep java | grep spring-boot:run
ps aux | grep java | grep spring-boot:run | awk {'print $2'} | xargs kill -9
cd ../
