package vip.codemonkey.data.jpa.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import vip.codemonkey.data.jpa.condition.LongIdCondition
import vip.codemonkey.data.jpa.entity.LongIdEntity
import vip.codemonkey.data.jpa.repository.QueryDslRepository
import java.util.*


/**
 *@author wang zhengtao
 *
 */
interface BaseService<E: LongIdEntity,C: LongIdCondition> {
    fun save(obj: E): E
    fun findById(id: Long): Optional<E>
    fun findAllById(ids: List<Long>): MutableList<E>
    fun findByCreatedAtAfter(createdAtStart: Date): List<E>
    fun findByCreatedAtBefore(createdAtEnd: Date): List<E>
    fun findByUpdatedAtAfter(updatedAtStart: Date): List<E>
    fun findByUpdatedAtBefore(updatedAtEnd: Date): List<E>
    fun findByCondition(condition: C): MutableList<E>
    fun findByCondition(condition: C, sort: Sort): MutableList<E>
    fun findByPageable(condition: C, pageable: Pageable): Page<E>
}