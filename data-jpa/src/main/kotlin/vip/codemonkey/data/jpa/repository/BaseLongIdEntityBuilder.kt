package vip.codemonkey.data.jpa.repository

import com.querydsl.core.BooleanBuilder
import vip.codemonkey.data.jpa.condition.LongIdCondition
import com.querydsl.core.types.Path
import vip.codemonkey.data.jpa.entity.LongIdEntity
import vip.codemonkey.data.jpa.entity.QLongIdEntity
import vip.codemonkey.common.extension.availableThen
import vip.codemonkey.common.extension.notEmptyThen
import vip.codemonkey.common.extension.notMinThen

/**
 *@author wang zhengtao
 *
 */
class BaseLongIdEntityBuilder{
    fun<C:LongIdCondition,ID:LongIdEntity> builder(condition:C,path:Path<ID>) = BooleanBuilder().apply{
        val qLongIdEntity = QLongIdEntity(path)
        val id = condition.id
        id.availableThen { and(qLongIdEntity.id.eq(id))}

        val ids = condition.ids
        ids.notEmptyThen { and(qLongIdEntity.id.`in`(ids)) }

        val createdAtStart = condition.createdAtStart
        createdAtStart.notMinThen { and(qLongIdEntity.createdAt.goe(createdAtStart)) }

        val createdAtEnd = condition.createdAtEnd
        createdAtEnd.notMinThen { and(qLongIdEntity.createdAt.loe(createdAtEnd)) }

        val updatedAtStart = condition.updatedAtStart
        updatedAtStart.notMinThen { and(qLongIdEntity.updatedAt.goe(updatedAtStart)) }

        val updatedAtEnd = condition.updatedAtEnd
        updatedAtEnd.notMinThen { and(qLongIdEntity.updatedAt.loe(updatedAtEnd)) }
    }

}