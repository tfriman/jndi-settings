package com.example;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import com.google.inject.Provider;
import javax.naming.Context;
import javax.naming.InitialContext;
import static com.google.inject.name.Names.named;
import static com.google.inject.jndi.JndiIntegration.*;

public class ExampleGuiceConfig extends JerseyServletModule {

    private final static String JNDI_ENV = "java:comp/env/";

    @Override
    protected void configureServlets() {
        bind(Context.class).to(InitialContext.class);
        getStringFromJndi("testString");
        bind(GuiceResource.class);

        serve("/*").with(GuiceContainer.class);
    }

    private void getStringFromJndi(String key) {
        Provider<String> stringProvider = fromJndi(String.class, JNDI_ENV + key);
        bind(String.class).annotatedWith(named(key)).toProvider(stringProvider);
    }
}
