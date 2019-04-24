package vip.codemonkey.sample.service.impl

import com.querydsl.core.types.Predicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vip.codemonkey.data.jpa.service.BaseServiceImpl
import vip.codemonkey.sample.condition.OrganizationCondition
import vip.codemonkey.sample.entity.Organization
import vip.codemonkey.sample.repository.OrganizationRepository
import vip.codemonkey.sample.repository.toPredicate
import vip.codemonkey.sample.service.OrganizationService


/**
 *@author wang zhengtao
 *
 */
@Service
class OrganizationServiceImplImpl: BaseServiceImpl<Organization, OrganizationCondition, OrganizationRepository>(),OrganizationService {
    @Autowired
    override lateinit var repository: OrganizationRepository

    override fun defaultPredicate(condition: OrganizationCondition): Predicate {
        return repository.toPredicate(condition)
    }
}