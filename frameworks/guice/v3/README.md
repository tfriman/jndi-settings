The simplest Guice + Jersey webapp gets one configuration String via JNDI. 

Run `mvn jetty:run` or `mvn tomcat7:run` and open [http://localhost:8080/](http://localhost:8080/).
You should see message from the corresponding configuration file (see src/test/resources/).