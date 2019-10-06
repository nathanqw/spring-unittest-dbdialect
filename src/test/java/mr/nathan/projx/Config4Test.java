package mr.nathan.projx;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class Config4Test {
	// https://www.baeldung.com/spring-jpa-test-in-memory-database
	// https://github.com/eugenp/tutorials/blob/master/persistence-modules/spring-jpa/src/main/java/org/baeldung/config/StudentJpaConfig.java
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "mr.nathan.projx" });
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		// required, or it won't find the db
		em.setJpaProperties(additionalProperties());
		
		return em;
	}

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		
		// not needed
		//transactionManager.setJpaProperties(additionalProperties());
		
		return transactionManager;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		
		// can use a dedicated test database
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projx");
		dataSource.setUsername("myusr");
		dataSource.setPassword("mypwd");

		return dataSource;
	}

	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.postgis.PostgisDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
		hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");

		// for silencing createClob exceptions
		hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");

		// override the default camelCase db table column naming, with the snake_case,
		// otherwise, it'd create additional (extra, duplicated) columns for the same fields
		hibernateProperties.setProperty("hibernate.physical_naming_strategy", 
				org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy.class.getCanonicalName());

		return hibernateProperties;
	}

}
