package vip.codemonkey.sample.repository

import vip.codemonkey.common.extension.notBlankThen
import vip.codemonkey.data.jpa.repository.BaseLongIdEntityBuilder
import vip.codemonkey.data.jpa.repository.QueryDslRepository
import vip.codemonkey.sample.condition.OrganizationCondition
import vip.codemonkey.sample.entity.Organization
import vip.codemonkey.sample.entity.QOrganization


/**
 *@author wang zhengtao
 *
 */
interface OrganizationRepository: QueryDslRepository<Organization, Long>

private var org = QOrganization.organization
fun OrganizationRepository.toPredicate(c: OrganizationCondition) = BaseLongIdEntityBuilder().builder(c,org).apply {
    c.code.notBlankThen { and(org.code.startsWith(c.code)) }
    c.name.notBlankThen { and(org.name.eq(c.name)) }
}