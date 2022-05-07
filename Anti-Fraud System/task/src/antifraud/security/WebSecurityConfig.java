package antifraud.security;

import antifraud.model.Role;
import antifraud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .antMatchers(HttpMethod.POST, "/api/auth/user/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/auth/user/**").hasAuthority(Role.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.GET, "/api/auth/list/**").hasAnyAuthority(Role.ADMINISTRATOR.name(), Role.SUPPORT.name())
                .antMatchers(HttpMethod.POST, "/api/antifraud/transaction/**").hasAuthority(Role.MERCHANT.name())
                .antMatchers(HttpMethod.PUT, "/api/auth/access/**").hasAuthority(Role.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.PUT, "/api/auth/role/**").hasAuthority(Role.ADMINISTRATOR.name())
                .antMatchers("/actuator/shutdown").permitAll() // needs to run test
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }
}
