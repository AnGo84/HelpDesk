package ua.helpdesk.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	private final DataSource dataSource;

	public SecurityConfiguration(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, DataSource dataSource) {
		this.userDetailsService = userDetailsService;
		this.dataSource = dataSource;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
				//.antMatchers("/resources/**").permitAll()
				//.antMatchers("/", "/index").permitAll()

				.antMatchers("/admin**").access("hasAnyRole('ROLE_ADMIN','ROLE_SUPPORT')")
				.antMatchers("/users", "/users/add", "/users/update", "/users/edit-**", "/users/delete-**", "/users/view-**", "/users/resetPassword-**").access("hasAnyRole('ROLE_ADMIN','ROLE_SUPPORT')")
				.antMatchers("/services/add", "/services/update", "/services/edit-**", "/services/delete-**").access("hasAnyRole('ROLE_ADMIN','ROLE_SUPPORT')")
				.antMatchers("/categories/add", "/categories/update", "/categories/edit-**", "/categories/delete-**").access("hasAnyRole('ROLE_ADMIN','ROLE_SUPPORT')")

				.antMatchers("/users/viewCurrent", "/users/change_password").authenticated()
				.antMatchers("/services", "/services/view**").authenticated()
				.antMatchers("/categories", "/categories/view**").authenticated()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("login")
				.passwordParameter("pass")
				//.defaultSuccessUrl("/tickets_list_page.html", true)
				//.loginProcessingUrl("/doLogin")
				.permitAll()
				.and()
				.logout()
				.permitAll()

				// Config Remember Me.
				.and() //
				.rememberMe()//
				.rememberMeCookieName("helpdesk-remember-me")//
				.tokenRepository(this.persistentTokenRepository()) //
				.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	// Token stored in Table (Persistent_Logins)
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

}
