package com.example.chatter.config;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    protected JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception{
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder getPassEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
                .antMatchers("/admin").hasRole("admin")
                .antMatchers("/user").authenticated()
                .antMatchers("/**").permitAll().and()
            .exceptionHandling()
                .authenticationEntryPoint(
                    (req, res, ex) -> res.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage()
                    )
                ).and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
