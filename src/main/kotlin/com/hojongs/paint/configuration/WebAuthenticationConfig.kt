package com.hojongs.paint.configuration

import com.hojongs.paint.repository.PaintUserRepository
import com.hojongs.paint.repository.model.PaintUser
import com.hojongs.paint.service.PaintUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class WebAuthenticationConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var paintUserRepository: PaintUserRepository

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    protected fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

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
        paintUserRepository
            .save(
                PaintUser(
                    "user@example.co",
                    "user",
                    passwordEncoder().encode("pw")
                )
            )

        auth.userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder())
    }

    override fun userDetailsService(): UserDetailsService = PaintUserService(paintUserRepository)
}
