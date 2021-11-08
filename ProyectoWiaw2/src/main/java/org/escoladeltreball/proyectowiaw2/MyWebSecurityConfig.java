package org.escoladeltreball.proyectowiaw2;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled  = true, securedEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{ //http://www.mkyong.com/spring-security/spring-security-form-login-using-database/
		//Convention over configuration
		auth.jdbcAuthentication().dataSource(dataSource)
		.authoritiesByUsernameQuery("select u.dni,a.autoridad from autoridad a join usuario u on a.usuario_id = u.id where dni = ?")
		.usersByUsernameQuery("SELECT dni, password, activo FROM usuario where dni = ?")
		.passwordEncoder(passwordEncoder);
//			from autoridad a join usuario u on a.usuario_usuario_id = u.usuario_id
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/resources/**","/webjars/**", "/login", "/contactUs", "/resetPassword", "/").permitAll()
		//.antMatchers("/").hasRole("USER")
		.antMatchers("/changePassword").hasAuthority("CHANGE_PASSWORD_AUTH")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/dash").failureUrl("/login?error").usernameParameter("dni")
	    //.successHandler(//declare your bean here) 
		.and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/") //La url on anar un cop fet el logout i la url de logout, per defecte ja es logout
		.and()
		.httpBasic()
		.and()
		.csrf().disable();
	}
	
	
}
