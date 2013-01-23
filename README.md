# JNDI settings

Contains JNDI settings for different application servers and frameworks.

## Purpose of this repository

Java web app package (usually WAR) should not contain any environmental specific configurations. The right place for those is in the JNDI.
Do *not* use any Maven profiles or Ant property file packaging mechanisms.

There will be examples of configurations for different application servers and frameworks in this repository.

## Definitions

Java Naming Directory Service ... see JNDI definition from Wiki or Oracle or [Google](https://www.google.com) if you need one.

## Application Servers

I have not tested these configurations with all different versions so caveat emptor.

### Jetty

#### Version 8

### Tomcat

#### Version 7

### JBoss
#### Version 6.1

## Accessing JNDI

Please note that these do not represent production ready code although
these examples should be complete (i.e. contain fully qualified class
names etc).

### Old skool

Traditional way to access.

```java
  try {
      javax.naming.Context initialContext = new javax.naming.InitialContext();
      javax.naming.Context envContext = (javax.naming.Context) initialContext.lookup("java:comp/env");
      String serverAddress = (String) envContext.lookup("serverAddress");
  } catch (javax.naming.NamingException e) {
      // HANDLE THIS!
      throw new RuntimeException("TODO", e);
  }
```

### Spring 

Here we are using Spring version 3.0.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jee="http://www.springframework.org/schema/jee"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.0.xsd
        >
    <jee:jndi-lookup id="serverPort" jndi-name="customApplicationConfig/serverPort" 
        lookup-on-startup="true" expected-type="java.lang.Long"/>
    <!-- other definitions here -->
</beans>
```

### Guice

I'm not that experienced with Guice but this worked. You need these in your pom.xml, I used Guice version 3.0.

```xml
<dependency>
  <groupId>com.google.inject</groupId>
   <artifactId>guice</artifactId>
   <version>${guice.version}</version>
  </dependency>
<dependency>
  <groupId>com.google.inject.extensions</groupId>
  <artifactId>guice-jndi</artifactId>
  <version>${guice.version}</version>
</dependency>
```

Module setup:
```java
public class ExampleModule extends com.google.inject.AbstractModule {

    private final static String JNDI_ENV = "java:comp/env/";

    @Override
    protected void configure() {
        bind(javax.naming.Context.class).to(javax.naming.InitialContext.class);
        String key = "serverAddress";
        com.google.inject.Provider<String> stringProvider = com.google.inject.jndi.JndiIntegration.fromJndi(String.class, JNDI_ENV + key);
        bind(String.class).annotatedWith(com.google.inject.name.Names.named(key)).toProvider(stringProvider);
    }
}
```

And injecting that to Bean:

```java
@com.google.inject.servlet.SessionScoped
public class Example {
    private String serverAddress;
    @com.google.inject.Inject
    public Example(@com.google.inject.name.Named("serverAddress") String serverAddress) {
        this.serverAddress = serverAddress;
   }
}
```
