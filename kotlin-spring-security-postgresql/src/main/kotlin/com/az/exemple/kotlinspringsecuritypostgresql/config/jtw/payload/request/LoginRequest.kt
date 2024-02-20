package com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.payload.request

import jakarta.validation.constraints.NotBlank


class LoginRequest {
    @NotBlank
    var username: String? = null

    @NotBlank
    var password: String? = null
}