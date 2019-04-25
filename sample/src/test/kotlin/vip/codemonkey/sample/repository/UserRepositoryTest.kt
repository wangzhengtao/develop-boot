package vip.codemonkey.sample.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import vip.codemonkey.data.jpa.repository.BaseDataTestCase
import org.junit.jupiter.api.Assertions

class UserRepositoryTest : BaseDataTestCase() {
    @Autowired
    lateinit var userRepository: UserRepository
    @Test
    fun testFindByUsername(){
        val optional = userRepository.findByUsername("admin")
        Assertions.assertTrue(optional.isPresent)
    }
}