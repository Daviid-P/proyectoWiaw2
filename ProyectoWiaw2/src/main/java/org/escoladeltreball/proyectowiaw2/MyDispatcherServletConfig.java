package org.escoladeltreball.proyectowiaw2;

import org.apache.commons.lang3.Conversion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@Import(MySpringContextConfig.class)
@EnableWebMvc
@ComponentScan
@PropertySources({
	@PropertySource(value= {"classpath:jdbc.properties","classpath:smtp.properties","classpath:app.properties"}) //Accepta varis fitxers, per aixo es posa com array
})
public class MyDispatcherServletConfig 
extends WebMvcConfigurerAdapter 
implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

/* ThymeLeaf Configuration */ 
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setCharacterEncoding("UTF8");
		return resolver;
	}
	
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.addDialect(new Java8TimeDialect());
		engine.addDialect(new SpringSecurityDialect());
		engine.setTemplateResolver(templateResolver());
		return engine;
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setCharacterEncoding("UTF8");
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/thymeleaf/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	/* ThymeLeaf Configuration Ends */
	
	
	/**
	 * Access resource files
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/images/avatar/**").addResourceLocations("file:/home/users/inf/wiaw2/iaw47752902/ProyectoWiaw2/proyectoWiaw2/avatar/",
																			  "file:/home/users/inf/wiaw2/iaw47891984/Documents/proyectoWiaw2/avatar/",
																			  "file:/D:/Users/David/Desktop/DAW/Segundo/proyectoWiaw2/avatar/",
																			  "file:/home/kevin/proyectoWiaw2/avatar/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}

	/**
	 * Configure static content handling
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

}
