package com.example;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
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
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws NamingException {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new FileSystemResource(getFilePathFromJndi()));
        propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return propertyPlaceholderConfigurer;
    }

    private String getFilePathFromJndi() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:/comp/env/configurationFilePath");
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setExpectedType(java.lang.String.class);
        jndiObjectFactoryBean.afterPropertiesSet(); // Yes, this is needed.
        return (String) jndiObjectFactoryBean.getObject();
    }
}
