package vip.codemonkey.sample.repository

import vip.codemonkey.data.jpa.repository.QueryDslRepository
import vip.codemonkey.sample.entity.User
import java.util.*

interface UserRepository : QueryDslRepository<User, Long> {
    fun findByUsername(username: String):Optional<User>
}