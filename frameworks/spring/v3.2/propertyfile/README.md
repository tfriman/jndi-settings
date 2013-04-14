The simplest Spring MVC XML configured webapp gets one configuration
file path via JNDI and reads it from the file system outside of the
web app.

Run `mvn jetty:run` or `mvn tomcat7:run` and open [http://localhost:8080/](http://localhost:8080/).
You should see message from the corresponding configuration file (see src/test/resources/ and etc/).
