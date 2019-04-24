package vip.codemonkey.data.jpa.repository

import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import vip.codemonkey.data.jpa.entity.IdEntity
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.EntityManager

/**
 *@author wang zhengtao
 *
 */
class CustomSimpleJpaRepository<T, ID : Serializable>(entityInformation: JpaEntityInformation<T, ID>, entityManager: EntityManager) : SimpleJpaRepository<T, ID>(entityInformation, entityManager) {

    override fun <S : T> save(entity: S): S {
        if (entity is IdEntity<*>) {
            entity.updatedAt = LocalDateTime.now()
        }
        return super.save(entity)
    }
}