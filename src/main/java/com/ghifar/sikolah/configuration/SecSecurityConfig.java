package com.ghifar.sikolah.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private DataSource securityDataSource;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource).usersByUsernameQuery("select username,password, enabled from user where username=?").passwordEncoder(new BCryptPasswordEncoder())
                .authoritiesByUsernameQuery("select user.username as username, role.name as role FROM user INNER JOIN user_roles ON user.id = user_roles.user_id INNER JOIN role ON user_roles.role_id = role.id WHERE user.username = ?");

//        auth.userDetailsService(new MyUserDetailsService());

//        auth.authenticationProvider(new MyCustomAuthProvider());


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/login","/resources*","/resources/**","/static/**","/register*","/register/**").permitAll()
                .antMatchers("/index","/").hasAnyRole("ADMIN","GURU","SISWA")
                .antMatchers("/users").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProcess")
                    .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login?logout")
                .permitAll()
//                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/403")
//                .and().csrf().disable()
        ;
    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }



//    dari eclipse liat eclipse
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }





}
