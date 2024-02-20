package com.az.exemple.kotlinspringsecuritypostgresql.controller

import com.az.exemple.kotlinspringsecuritypostgresql.config.jtw.payload.request.SignupRequest
import com.az.exemple.kotlinspringsecuritypostgresql.domain.User
import com.az.exemple.kotlinspringsecuritypostgresql.domain.repositories.RoleRepository
import com.az.exemple.kotlinspringsecuritypostgresql.domain.repositories.UserRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/user")
class UserController {


    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null


    @GetMapping("/public")
    fun allAccess(): String {
        return "Public Content."
    }

    @GetMapping("/signed")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun userAccess(): String {
        return "User Content."
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess(): String {
        return "Moderator Board."
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "Admin Board."
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun findAll(): MutableList<User?> {
        return userRepository!!.findAll()
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun findOne(@PathVariable id : Long): User {
        return userRepository!!.findById(id).get()
    }

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id : Long): String {
        val user = userRepository!!.findById(id).get()
        userRepository!!.deleteById(id)
        return "User ${user.username} Deleted"
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody signUpRequest: @Valid SignupRequest?): String {
        val user = userRepository!!.findById(signUpRequest?.id!!).get()
        user.username = signUpRequest.username
        user.email = signUpRequest.email
        userRepository?.save(user)
        return "User ${user.username} Updated"
    }



}