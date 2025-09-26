mvn clean install -Dmaven.test.skip=true
cd browse
mvn spring-boot:run &
cd ../manage
mvn clean install
cd ../browse
mvn spring-boot:stop
ps aux | grep java | grep au.com.practica.src.challenge.BrowseApplication | awk {'print $2'} | xargs kill -9
cd ../
