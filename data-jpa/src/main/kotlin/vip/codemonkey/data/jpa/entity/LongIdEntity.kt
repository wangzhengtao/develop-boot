package vip.codemonkey.data.jpa.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import vip.codemonkey.common.constant.DateTimePattern.Companion.DATE_TIME_PATTERN
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*


/**
 *@author wang zhengtao
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class LongIdEntity(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @get:CreatedDate
    @get:JsonFormat(pattern = DATE_TIME_PATTERN)
    @get:Column(name = "created_at", insertable = false, updatable = false)
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    @get:LastModifiedDate
    @get:JsonFormat(pattern = DATE_TIME_PATTERN)
    @get:Column(name = "updated_at", insertable = false)
    override var updatedAt: LocalDateTime = LocalDateTime.now()
):IdEntity<Long>