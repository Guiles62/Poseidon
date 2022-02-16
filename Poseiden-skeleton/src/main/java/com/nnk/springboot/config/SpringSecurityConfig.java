package com.nnk.springboot.config;


import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/app/home").permitAll()
                .antMatchers("/user/add").permitAll()
                .antMatchers("/admin/home").hasAnyAuthority("ADMIN")
                .antMatchers("/user/list").hasAnyAuthority("ADMIN")
                .antMatchers("/app/secure/article-details").hasAnyAuthority("ADMIN")
                .antMatchers("/bidList/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/bidList/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers("/curvePoint/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/curvePoint/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers("/rating/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/rating/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers("/ruleName/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/ruleName/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers("/trade/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/trade/delete/{id}").hasAnyAuthority("ADMIN")
                .antMatchers("/user/update/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user/delete/{id}").hasAnyAuthority("ADMIN")



                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/bidList/list").failureUrl("/")
                .usernameParameter("userName")
                .passwordParameter("password")
                .and().logout().deleteCookies("JSESSIONID").logoutUrl("/app-logout").logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login").defaultSuccessUrl("/bidList/list").failureUrl("/")
                .and()
                .httpBasic();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
