package vip.codemonkey.sample.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import vip.codemonkey.sample.condition.OrganizationCondition
import vip.codemonkey.sample.entity.Organization
import vip.codemonkey.sample.service.OrganizationService
import vip.codemonkey.common.dto.SimpleResponse


/**
 *@author wang zhengtao
 *
 */
@RestController
@RequestMapping("organization")
class OrganizationController {

    @Autowired
    lateinit var service: OrganizationService

    @GetMapping("{id}")
    fun detail(@PathVariable id:Long): SimpleResponse<Organization> {
        val optional = service.findById(id)
        if(optional.isPresent){
            return SimpleResponse.success(optional.get())
        }else{
            return SimpleResponse.success("未查询到结果")
        }
    }

    @PostMapping
    fun save(@RequestBody organization: Organization): SimpleResponse<Organization> {
        //TODO add param validation
        service.save(organization)
        return SimpleResponse.success(organization)
    }

    /**
     * 更新所有属性
     */
    @PutMapping("{id}")
    fun update(@RequestBody organization: Organization,@PathVariable id: Long): SimpleResponse<Organization> {
        val original  = service.findById(id)
        if(!original.isPresent){
            return SimpleResponse.failed("该数据不存在")
        }
        service.save(organization)
        return SimpleResponse.success(organization)
    }

    /**
     * 更新部分属性
     */
    @PatchMapping("{id}")
    fun patch(@RequestBody organization: Organization,@PathVariable id: Long): SimpleResponse<Organization> {
        val original  = service.findById(id)
        if(!original.isPresent){
            return SimpleResponse.failed("该数据不存在")
        }
        //TODO copy properties
        service.save(organization)
        return SimpleResponse.success(organization)
    }

    @PostMapping("page")
    fun page(@RequestBody condition: OrganizationCondition,@PageableDefault pageable: Pageable): SimpleResponse<Page<Organization>> {
        val page = service.findByPageable(condition, pageable)
        return SimpleResponse.success(page)
    }

}