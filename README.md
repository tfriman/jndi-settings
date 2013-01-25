# JNDI settings

Contains JNDI settings for different application servers and frameworks.

## Purpose of this repository

Java web app package (usually WAR) should not contain any
environmental specific configurations. The right place for those is in
the JNDI. [Java Naming and Directory
Interface](http://en.wikipedia.org/wiki/Java_Naming_and_Directory_Interface)
is the standard way looking up objects and data via a name. Most Java
application servers provide it and hence provide standard locations
for configuration parameters.

There are few examples of configurations for different application
servers and frameworks in this repository. If you have configuration
examples not found here, please send pull request. Please note that
"standard" JNDI configurations like database source configurations
have been excluded, there are myriads of examples of those in the Web.

## Disclaimers

These are examples, NOT production ready solutions. Exception handling
is missing as well as logging. There are no tests. Naming is horrible.

Examples have been tested on OS X (Mountain Lion) using Java 1.6.0_37
and Maven 3.0.4 only.

Examples just read in java.lang.String using env-entries. One could
use that java.lang.String as file path and load configurations from
there. Please note that env-entries can basically contain only
java.lang.*. See specification for more info.

## Application Servers

Examples contain Jetty 8.1 and Tomcat 7 embedded with configurations
that can be used with stand alone versions.

There is an excerpt of configuration for version JBoss 6.1. 

More is needed.

## Accessing JNDI

Please note that these do not represent production ready code although
these examples should be complete (i.e. contain fully qualified class
names etc).

### Old skool

Traditional way to access JNDI. 

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

Here we are using Spring version 3.2.

#### XML configuration:

jee:jndi-lookup -tag basically uses JndiObjectFactoryBean, see it's javadoc.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jee="http://www.springframework.org/schema/jee"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.2.xsd
        >
    <jee:jndi-lookup id="serverPort" jndi-name=""java:comp/env/serverPort" 
        lookup-on-startup="true" expected-type="java.lang.Long"/>

    <!-- this works too, because java:comp/env is the default search path in the JndiObjectFactoryBean,
         see setJndiName(String jndiName) javadoc:
         Specify the JNDI name to look up. If it doesn't begin with "java:comp/env/"
         this prefix is added automatically if "resourceRef" is set to "true".-->
    <!--
    <jee:jndi-lookup id="serverPort" jndi-name="serverPort"
                     lookup-on-startup="true" expected-type="java.lang.Long"/>
    -->    

    <!-- other definitions here -->
</beans>
```

#### Java configuration:

There is no annotation for jee:jndi-lookup but it can be circumvented
using JndiObjectFactoryBean directly (the same way jee:jndi-lookup
does it under the hood)

```java
package com.example;

@org.springframework.web.servlet.config.annotation.EnableWebMvc
@org.springframework.context.annotation.Configuration
@org.springframework.context.annotation.ComponentScan(basePackages = "com.example")
public class AppConfig extends org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter {

    @org.springframework.context.annotation.Bean
    public String messageFromJndi() throws javax.naming.NamingException {
        org.springframework.jndi.JndiObjectFactoryBean jndiObjectFactoryBean = new org.springframework.jndi.JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:/comp/env/testString");
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setExpectedType(String.class);
        jndiObjectFactoryBean.afterPropertiesSet(); // Yes, this is needed.
        return (String) jndiObjectFactoryBean.getObject();
    }
}
```
### Guice

Disclaimer: I have not used Guice, this example is just made up but it
_seems_ to work. You need these in your pom.xml, I used Guice version
3.0.

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

And injecting that to Example:

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

## TODO 

* Example of reading the configuration file.
* JNDI namespaces
* Gists instead of those embedded examples on this page... is it possible?


