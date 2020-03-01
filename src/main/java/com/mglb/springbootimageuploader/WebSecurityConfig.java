package com.mglb.springbootimageuploader;

import com.mglb.springbootimageuploader.model.AppUser;
import com.mglb.springbootimageuploader.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
// WebSecurityConfigurerAdapter - provides methods to secure endpoints
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private AppUserRepo appUserRepo;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepo appUserRepo) {
        this.userDetailsService = userDetailsService;
        this.appUserRepo = appUserRepo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService); // class responsible to connecting with DB
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/gallery").hasAnyRole("USER", "ADMIN")
                .antMatchers("/upload").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable()
        // cross-site request forgery error when working with vaadim
        // csrf - zapezpieczenie springa zeby nie laczyc sie z zewnetrzengo hosta
                .httpBasic();  // for postman

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // new user on the start of aplication
    @EventListener(ApplicationReadyEvent.class)
    public void initializeUsers(){
        AppUser appUser1 = new AppUser("user", passwordEncoder().encode("password"), "ROLE_USER");
        AppUser appUser2 = new AppUser("admin", passwordEncoder().encode("password"), "ROLE_ADMIN");

        appUserRepo.save(appUser1);
        appUserRepo.save(appUser2);
    }
}
