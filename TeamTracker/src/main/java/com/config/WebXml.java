package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.config.security.AuthenticationConfig;
import com.util.PropertyReader;
  
/**
 * web.xml content with annotation, Bean configurator and Controller loader
 * 
 * @author M1031956
 *
 */
@Configuration
@ComponentScan("com.controller,com.service,com.dao")
@EnableWebMvc  
@Import({ AuthenticationConfig.class })
public class WebXml extends WebMvcConfigurerAdapter {
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

	    boolean mysql=true;
//	    mysql=false;
	    if (mysql) {
			driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
			driverManagerDataSource.setUrl(PropertyReader.getValue("URL"));
			driverManagerDataSource.setUsername("root");
			driverManagerDataSource.setPassword("MtreeAol2016$");
		}else{
			driverManagerDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		    driverManagerDataSource.setUrl("jdbc:hsqldb:file:/hsqldb-team/teamTracker");
		    driverManagerDataSource.setUsername("sa");
		    driverManagerDataSource.setPassword("");
		}
	    
	    return driverManagerDataSource;
	}
      
    @Bean  
    public InternalResourceViewResolver setupViewResolver() {  
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");  
        resolver.setSuffix(".jsp");  
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    }  
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);   // 20MB
        multipartResolver.setMaxInMemorySize(1048576);  // 1MB
        return multipartResolver;
    }
} 

