package com.az.exemple.kotlinspringsecuritypostgresql.domain.repositories

import com.az.exemple.kotlinspringsecuritypostgresql.domain.ERole
import com.az.exemple.kotlinspringsecuritypostgresql.domain.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: ERole?): Optional<Role?>?
}