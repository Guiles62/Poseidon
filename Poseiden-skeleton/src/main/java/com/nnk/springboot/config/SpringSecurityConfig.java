package com.nnk.springboot.config;


import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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
                .antMatchers("/user/**").permitAll()
                .antMatchers("/bidList/**").authenticated()
                .antMatchers("/curvePoint/**").authenticated()
                .antMatchers("/rating/**").authenticated()
                .antMatchers("/ruleName/**").authenticated()
                .antMatchers("/trade/**").authenticated()
                .antMatchers("/bidList/update").hasAuthority("ROLE_ADMIN")
                .antMatchers("/curvePoint/update").hasRole("ADMIN")
                .antMatchers("/rating/update").hasRole("ADMIN")
                .antMatchers("/ruleName/update").hasRole("ADMIN")
                .antMatchers("/trade/update").hasRole("ADMIN")
                .antMatchers("/user/update").hasRole("ADMIN")


                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/bidList/list").failureUrl("/403")
                .usernameParameter("userName")
                .passwordParameter("password")
                .and().logout().deleteCookies("JSESSIONID").logoutUrl("/app-logout").logoutSuccessUrl("/")
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
