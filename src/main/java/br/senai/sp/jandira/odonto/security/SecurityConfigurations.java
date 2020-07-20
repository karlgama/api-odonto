package br.senai.sp.jandira.odonto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AutenticacaoService autenticacaoService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            
            /* User permissions */
            .antMatchers(HttpMethod.GET, "/odonto/dentistas").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/odonto/dentistas").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/odonto/dentistas/upload").hasRole("USER")
            .antMatchers(HttpMethod.PUT, "/odonto/dentistas/*").hasRole("USER")

            /* Admin permissions */
            .antMatchers("/odonto/**").hasRole("ADMIN")

            .anyRequest().denyAll()
            .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(autenticacaoService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}