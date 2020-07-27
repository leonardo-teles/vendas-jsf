package com.algaworks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public AppUserDetailsService userDetailsSerivice() {
		return new AppUserDetailsService();
	}
	
	@Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/negado.xhtml");
		jsfDeniedEntry.setContextRelative(true);
		
		http
			.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()
		.authorizeRequests()
			.antMatchers("/login.xhtml", "/erro.xhtml", "/javax.faces.resource/**").permitAll()
			.antMatchers("/index.xhtml", "/negado.xhtml").authenticated()
			.antMatchers("/pedidos/**", "/clientes/**", "/dialogos/**").hasAnyRole("VENDEDORES", "AUXILIARES", "ADMINISTRADORES")
			.antMatchers("/produtos/**", "/usuarios/**", "/relatorios/**").hasRole("ADMINISTRADORES")
			.and()
		.formLogin()
			.loginPage("/login.xhtml")
			.failureUrl("/login.xhtml?invalid=true")
			.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
		.exceptionHandling()
			.accessDeniedPage("/negado.xhtml")
			.authenticationEntryPoint(jsfLoginEntry)
			.accessDeniedHandler(jsfDeniedEntry);
	}
}
