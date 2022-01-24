/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
        private DataSource dataSource;
        @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
                        .csrf().disable()
			.authorizeRequests()
                                .antMatchers(HttpMethod.POST, "/postregistration","/postresetpassword","/postresetpasswordform").permitAll()
                                .antMatchers("/","/home","/registration","/confirm","/resetpassword","/resetpasswordform").permitAll()

                                .anyRequest().authenticated()
				.and()

			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();

	}

	@Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.jdbcAuthentication()
                    .dataSource( dataSource)
                    .passwordEncoder(NoOpPasswordEncoder.getInstance())
                    .usersByUsernameQuery("select email as username, password, active from user where user.activated = 1 AND email = ?")
                    .authoritiesByUsernameQuery("SELECT user.email as username, user_role.role FROM user inner join user_role ON user.id = user_role.user_id where user.email = ?");
                    
        }
}
