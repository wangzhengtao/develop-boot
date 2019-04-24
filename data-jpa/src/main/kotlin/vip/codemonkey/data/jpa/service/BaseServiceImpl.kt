package vip.codemonkey.data.jpa.service

import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import vip.codemonkey.data.jpa.condition.LongIdCondition
import vip.codemonkey.data.jpa.entity.LongIdEntity
import vip.codemonkey.data.jpa.repository.QueryDslRepository
import java.util.*


/**
 *@author wang zhengtao
 *
 */
@Transactional
abstract class BaseServiceImpl<E: LongIdEntity,C:LongIdCondition,R:QueryDslRepository<E, Long>>:BaseService<E,C> {
    abstract var repository: R
    abstract fun defaultPredicate(condition: C):Predicate

    override fun save(obj:E): E {
        return repository.save(obj)
    }

    override fun findById(id:Long): Optional<E> {
        return repository.findById(id)
    }

    override fun findAllById(ids:List<Long>): MutableList<E> {
        return repository.findAllById(ids)
    }

    /**
     * time < 创建时间
     * @param createdAtStart 时间
     */
    override fun findByCreatedAtAfter(createdAtStart: Date): List<E>{
        return repository.findByCreatedAtAfter(createdAtStart)
    }

    /**
     * time > 创建时间
     * @param createdAtEnd 时间
     */
    override fun findByCreatedAtBefore(createdAtEnd: Date): List<E>{
        return repository.findByCreatedAtBefore(createdAtEnd)
    }

    /**
     * time < 修改时间
     * @param updatedAtStart 时间
     */
    override fun findByUpdatedAtAfter(updatedAtStart: Date): List<E>{
        return repository.findByUpdatedAtAfter(updatedAtStart)
    }

    /**
     * time > 修改时间
     * @param updatedAtEnd 时间
     */
    override fun findByUpdatedAtBefore(updatedAtEnd: Date): List<E>{
        return repository.findByUpdatedAtBefore(updatedAtEnd)
    }

    override fun findByCondition(condition:C): MutableList<E> {
        return repository.findAll(defaultPredicate(condition)).toMutableList()
    }

    override fun findByCondition(condition:C,sort:Sort): MutableList<E> {
        return repository.findAll(defaultPredicate(condition),sort).toMutableList()
    }

    override fun findByPageable(condition:C,pageable:Pageable): Page<E> {
        return repository.findAll(defaultPredicate(condition),pageable)
    }

}