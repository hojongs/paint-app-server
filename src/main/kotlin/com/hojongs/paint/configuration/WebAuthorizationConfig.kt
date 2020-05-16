package com.hojongs.paint.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebAuthorizationConfig
