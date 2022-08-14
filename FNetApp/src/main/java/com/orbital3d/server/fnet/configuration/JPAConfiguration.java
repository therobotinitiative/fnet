package com.orbital3d.server.fnet.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JPA configuration.
 * 
 * @author msiren
 *
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class JPAConfiguration {

	@Value("${datasource.driver-class-name:org.mariadb.jdbc.Driver}")
	private String jdbcDriverName;

	@Value("${datasource.user}")
	private String dataSourceUserName;

	@Value("${datasource.password}")
	private String dataSourcePassword;

	@Value("${datasource.url}")
	private String dataSourceURL;

	@Value("${hibernate.dialect:org.hibernate.dialect.MariaDB103Dialect}")
	private String hibernateDialect;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(new String[] { "com.orbital3d.server.fnet.database.entity",
				"com.orbital3d.server.fnet.database.repsitory" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setJpaProperties(additionalProperties());

		return entityManagerFactory;
	}

	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// Data source properties
		dataSource.setDriverClass(jdbcDriverName);

		dataSource.setUser(dataSourceUserName);
		dataSource.setPassword(dataSourcePassword);
		dataSource.setJdbcUrl(dataSourceURL);

		return dataSource;
	}

	/**
	 * @return Additional hibernate related properties
	 */
	Properties additionalProperties() {
		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", "none");
		properties.setProperty("hibernate.dialect", hibernateDialect);
		properties.setProperty("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
		properties.setProperty("hibernate.physical_naming_strategy",
				CamelCaseToUnderscoresNamingStrategy.class.getName());

		return properties;
	}
}
