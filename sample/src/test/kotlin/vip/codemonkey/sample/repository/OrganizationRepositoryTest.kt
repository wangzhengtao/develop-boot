package vip.codemonkey.sample.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import vip.codemonkey.common.number.Number36
import vip.codemonkey.data.jpa.repository.BaseDataTestCase
import vip.codemonkey.sample.condition.OrganizationCondition
import vip.codemonkey.sample.entity.Organization
import org.springframework.data.domain.PageRequest
import java.util.*


/**
 *@author wang zhengtao
 *
 */
class OrganizationRepositoryTest : BaseDataTestCase() {

    @Autowired
    lateinit var repository: OrganizationRepository

    @Test
    fun testFindById() {
        val id = -1L
        Assertions.assertEquals(repository.findById(id).get().code, "0000000001")
    }

    @Test
    fun testSave() {
        val organization = Organization("0000000002","龙门镖局")
        val save = repository.save(organization)
        logger.debug("save result is {}",save)
        Assertions.assertEquals(save.code,organization.code)
        Assertions.assertNotNull(save.createdAt)
    }

    @Test
    fun testUpdate() {
        val id = -1L
        val organization = repository.findById(id).get()
        val createdAt = organization.createdAt
        val updatedAt = organization.updatedAt
        val code = organization.code
        organization.code = Number36().add(organization.code,1)
        val afterUpdate = repository.save(organization)
        Assertions.assertTrue(Objects.equals(createdAt,afterUpdate.createdAt) && !Objects.equals(updatedAt,afterUpdate.updatedAt) && !Objects.equals(code,afterUpdate.code) )
    }

    @Test
    fun testFindByCondition() {
        val condition = OrganizationCondition("0000000001")
        val orgList = repository.findAll(repository.toPredicate(condition)).toMutableList()
        Assertions.assertTrue(orgList.size == 1)
        condition.id = -2L
        val emptyList = repository.findAll(repository.toPredicate(condition)).toMutableList()
        Assertions.assertTrue(emptyList.isEmpty())
    }

    @Test
    fun testFindByPageable() {
        val condition = OrganizationCondition("0000000001")
        val pageable = PageRequest.of(2,10)
        val page = repository.findAll(repository.toPredicate(condition), pageable)
        Assertions.assertEquals(page.totalElements,1)
        Assertions.assertEquals(page.number,2)
    }

}
