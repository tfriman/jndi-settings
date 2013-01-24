package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.naming.NamingException;

/**
 * Application configuration.
 *
 * @author Timo Friman
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public String messageFromJndi() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:/comp/env/testString");
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setExpectedType(java.lang.String.class);
        jndiObjectFactoryBean.afterPropertiesSet(); // Yes, this is needed.
        return (String) jndiObjectFactoryBean.getObject();
    }
}
