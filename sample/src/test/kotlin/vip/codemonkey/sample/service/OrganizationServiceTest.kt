package vip.codemonkey.sample.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import vip.codemonkey.data.jpa.repository.BaseDataTestCase


/**
 *@author wang zhengtao
 *
 */
class OrganizationServiceTest:BaseDataTestCase() {
    @Autowired
    lateinit var service:OrganizationService

    @Test
    fun testFindById(){
        val id = -1L
        val result = service.findById(id)
        Assertions.assertTrue(result.isPresent)
    }
}