package org.escoladeltreball.proyectowiaw2;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.escoladeltreball.proyectowiaw2.mail.Inbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories
@PropertySources({
	@PropertySource(value= {"classpath:jdbc.properties","classpath:smtp.properties","classpath:app.properties"}) //Accepta varis fitxers, per aixo es posa com array
})
public class MySpringContextConfig {
	
	@Autowired
	private Environment env;
	
	@Bean(name="dataSource")
	public BasicDataSource dataSource(){
		BasicDataSource dataSource = new BasicDataSource();
		
		String url = env.getProperty("jdbc.url");
		String driver = env.getProperty("jdbc.driver");
		String username = env.getProperty("jdbc.username");
		String password = env.getProperty("jdbc.password");
		
		dataSource.setUrl(url);
		dataSource.setDriverClassName(driver);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		return template;
	}
	
	/*Docs:
	 * http://stackoverflow.com/questions/30667711/jpa-entitymangerfactory-not-found-through-persistence-createentitymanagerfactory
	 * http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/jpa/LocalContainerEntityManagerFactoryBean.html
	 */
	//Entity Manager Factory 
	@Bean
	public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(){
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		
		localContainerEntityManagerFactoryBean.setPersistenceUnitName("proyectowiaw2");
		localContainerEntityManagerFactoryBean.setDataSource(dataSource());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		localContainerEntityManagerFactoryBean.setPackagesToScan("org.escoladeltreball.proyectowiaw2");
		
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		jpaProperties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		//jpaProperties.put("hibernate.hbm2ddl.import_files", env.getProperty("hibernate.hbm2ddl.import_files"));
		jpaProperties.put("hibernate.connection.characterEncoding", env.getProperty("hibernate.connection.characterEncoding"));
		jpaProperties.put("hibernate.connection.CharSet", env.getProperty("hibernate.connection.CharSet"));
		
		localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
		
		return localContainerEntityManagerFactoryBean;
	}
	
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(){
		
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
			localEntityManagerFactory().getObject());
		
		return jpaTransactionManager;
	}
	
	
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	
	//Method d'encriptacio dels passwords
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	// Mail Sending
	@Bean(value = "mailSender")
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
 
        mailSender.setHost(env.getProperty("mail.smtp.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("mail.smtp.port")));
 
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.debug", env.getProperty("mail.debug"));
 
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
		
	// Mail Sending
	@Bean(value = "inbox")
	public Inbox inbox() {
		Inbox inbox = new Inbox();
		inbox.setHost(env.getProperty("mail.imaps.host"));
		inbox.setPort(env.getProperty("mail.imaps.port"));
		
		return inbox;
    }
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		
		int maxFileSizeKB = 512;
		
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(maxFileSizeKB*1024);
	    multipartResolver.setMaxUploadSizePerFile(maxFileSizeKB*1024);
	    return multipartResolver;
	}

}
