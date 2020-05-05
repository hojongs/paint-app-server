package com.hojongs.paint.configuration

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebAuthorizationConfig
