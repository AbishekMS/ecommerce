package com.example.ecommerce.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.nio.file.attribute.UserPrincipal;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean    // we're defining this as bean just because in SpringBootWebSecurityConfiguration documentation they have mentioned: the user specifies their own SecurityFilterChain BEAN
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests(requests -> requests
                                 .requestMatchers("/order-items/**","/update/*/status/*","/user/*","/user").permitAll()
                                 .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
        //http.formLogin(withDefaults());
    }

    @Bean
    public AuthenticationProvider  authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


  /*  @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user= User.withUsername("user1")
                .password("{noop}password")
                .roles("USER")
                .build();
        UserDetails admin= User.withUsername("admin")
                .password("{noop}adminPass")
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager userDetailsManager= new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);
        //return new InMemoryUserDetailsManager(user,admin) ;
    }*/

}
/*AbstractHttpConfigurer: disable method()=
public B disable() {
		getBuilder().removeConfigurer(getClass());
		return getBuilder();
	}
*/