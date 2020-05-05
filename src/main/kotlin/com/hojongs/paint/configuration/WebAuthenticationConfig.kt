package com.hojongs.paint.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@EnableWebSecurity
class WebAuthenticationConfig : WebSecurityConfigurerAdapter() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests { registry ->
                registry
                    .antMatchers("/").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin { config -> config.permitAll() }
            .logout { config -> config.permitAll() }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun userDetailsService(): UserDetailsService =
        InMemoryUserDetailsManager(
            User.builder()
                .username("hojong")
                .password(passwordEncoder().encode("password"))
                .roles("USER").build(),
            User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN").build()
        )
}
