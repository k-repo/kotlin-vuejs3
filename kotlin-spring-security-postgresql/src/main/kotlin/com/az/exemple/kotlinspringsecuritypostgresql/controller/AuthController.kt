package com.az.exemple.kotlinspringsecuritypostgresql.controller

import com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.JwtUtils
import com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.payload.request.LoginRequest
import com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.payload.request.SignupRequest
import com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.payload.response.JwtResponse
import com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.payload.response.MessageResponse
import com.az.exemple.kotlinspringsecuritypostgresql.domain.ERole
import com.az.exemple.kotlinspringsecuritypostgresql.domain.Role
import com.az.exemple.kotlinspringsecuritypostgresql.domain.User
import com.az.exemple.kotlinspringsecuritypostgresql.domain.repositories.RoleRepository
import com.az.exemple.kotlinspringsecuritypostgresql.domain.repositories.UserRepository
import com.az.exemple.kotlinspringsecuritypostgresql.service.UserDetailsImpl
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import java.util.function.Supplier
import java.util.stream.Collectors


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(loginRequest!!.username, loginRequest.password))

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)

        val userDetails = authentication.getPrincipal() as UserDetailsImpl
        val roles = userDetails.authorities.stream().map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())

        return ResponseEntity
            .ok(JwtResponse(jwt, userDetails.id, userDetails.username, userDetails.email, roles))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignupRequest?): ResponseEntity<*> {
        if (userRepository!!.existsByUsername(signUpRequest!!.username)!!) {
            return ResponseEntity.badRequest().body(MessageResponse("Error: Username is already taken!"))
        }

        if (userRepository!!.existsByEmail(signUpRequest.email)!!) {
            return ResponseEntity.badRequest().body(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user: User = User(
            signUpRequest.username, signUpRequest.email,
            encoder!!.encode(signUpRequest.password)
        )

        val strRoles = signUpRequest.role
        val roles: MutableSet<Role> = HashSet<Role>()

        if (strRoles == null) {
            val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                ?.orElseThrow(Supplier {
                    RuntimeException(
                        "Error: Role is not found."
                    )
                })!!
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer<String> { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole: Role = roleRepository!!.findByName(ERole.ROLE_ADMIN)
                            ?.orElseThrow(Supplier {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            })!!
                        roles.add(adminRole)
                    }

                    "mod" -> {
                        val modRole: Role = roleRepository!!.findByName(ERole.ROLE_MODERATOR)
                            ?.orElseThrow(Supplier {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            })!!
                        roles.add(modRole)
                    }

                    else -> {
                        val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                            ?.orElseThrow(Supplier {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            })!!
                        roles.add(userRole)
                    }
                }
            })
        }

        user.roles = roles
        userRepository!!.save<User>(user)

        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}