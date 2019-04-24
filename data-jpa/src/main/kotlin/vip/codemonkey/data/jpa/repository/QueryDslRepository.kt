package vip.codemonkey.data.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import vip.codemonkey.data.jpa.entity.LongIdEntity
import java.util.*

/**
 *@author wang zhengtao
 *
 */
interface QueryDslRepository<T : LongIdEntity, Long> : JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {

    /**
     * time < 创建时间
     * @param createdAtStart 时间
     */
    fun findByCreatedAtAfter(createdAtStart: Date): List<T>

    /**
     * time > 创建时间
     * @param createdAtEnd 时间
     */
    fun findByCreatedAtBefore(createdAtEnd: Date): List<T>

    /**
     * time < 修改时间
     * @param updatedAtStart 时间
     */
    fun findByUpdatedAtAfter(updatedAtStart: Date): List<T>

    /**
     * time > 修改时间
     * @param updatedAtEnd 时间
     */
    fun findByUpdatedAtBefore(updatedAtEnd: Date): List<T>
}
