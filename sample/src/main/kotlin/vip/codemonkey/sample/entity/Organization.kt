package vip.codemonkey.sample.entity

import vip.codemonkey.common.number.Number36
import vip.codemonkey.data.jpa.entity.LongIdEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


/**
 *@author wang zhengtao
 *
 */
@Entity
@Table(name = "sys_organization")
data class Organization(
    /**
     * 10位36进制编码，全局唯一
     */
    @get:Column(nullable = false, length = 10,unique = true)
    var code: String = Number36().getInitNumber(10),
    @get:Column(nullable = false, length = 50,unique = true)
    var name: String = ""
): LongIdEntity()