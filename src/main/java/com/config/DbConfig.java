package com.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class DbConfig {

	@Value("${connection.url}")
	private String url;

	@Value("${connection.driverClassName}")
	private String driverClassName;

	@Value("${connection.username}")
	private String username;

	@Value("${connection.password}")
	private String password;

	@Value("${connection.jndiName}")
	private String jndiName;

	@Bean
	@Profile("dev")
	public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		return new TomcatEmbeddedServletContainerFactory() {

			// Enable JNDI
			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			// Configure DataSource
			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();

				resource.setName(jndiName);
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", driverClassName);
				resource.setProperty("url", url);
				resource.setProperty("password", password);
				resource.setProperty("username", username);

				context.getNamingResources().addResource(resource);
			}
		};
	}

	// Lookup
	@Bean(destroyMethod = "")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/" + jndiName);
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
}