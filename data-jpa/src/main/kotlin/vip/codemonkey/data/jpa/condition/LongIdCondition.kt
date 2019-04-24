package vip.codemonkey.data.jpa.condition

import java.io.Serializable
import java.time.LocalDateTime


/**
 *@author wang zhengtao
 *
 */
open class LongIdCondition(
    var id: Long = 0L,
    var ids: MutableList<Long> = mutableListOf(),
    var createdAtStart: LocalDateTime = LocalDateTime.MIN,
    var createdAtEnd: LocalDateTime = LocalDateTime.MIN,
    var updatedAtStart: LocalDateTime = LocalDateTime.MIN,
    var updatedAtEnd: LocalDateTime = LocalDateTime.MIN
):Serializable