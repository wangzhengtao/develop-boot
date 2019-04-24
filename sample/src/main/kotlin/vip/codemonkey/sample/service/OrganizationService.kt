package vip.codemonkey.sample.service

import vip.codemonkey.data.jpa.service.BaseService
import vip.codemonkey.sample.condition.OrganizationCondition
import vip.codemonkey.sample.entity.Organization


/**
 *@author wang zhengtao
 *
 */
interface OrganizationService:BaseService<Organization,OrganizationCondition>