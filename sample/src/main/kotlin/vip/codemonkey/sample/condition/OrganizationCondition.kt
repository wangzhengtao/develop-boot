package vip.codemonkey.sample.condition

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import vip.codemonkey.data.jpa.condition.LongIdCondition


/**
 *@author wang zhengtao
 *
 */
data class OrganizationCondition(
    var code:String = "",
    var name:String = ""
): LongIdCondition()