package com.az.exemple.kotlinspringsecuritypostgresql.service

import com.az.exemple.kotlinspringsecuritypostgresql.domain.User
import com.az.exemple.kotlinspringsecuritypostgresql.domain.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.function.Supplier


@Service
class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    var userRepository: UserRepository? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository!!.findByUsername(username)
            ?.orElseThrow(Supplier {
                UsernameNotFoundException(
                    "User Not Found with username: $username"
                )
            })!!

        return UserDetailsImpl.build(user)
    }
}