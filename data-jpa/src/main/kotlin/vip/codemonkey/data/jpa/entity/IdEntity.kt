package vip.codemonkey.data.jpa.entity

import java.io.Serializable
import java.time.LocalDateTime


/**
 *@author wang zhengtao
 *
 */
interface IdEntity<T: Serializable> :Serializable {
    var id: T
    var createdAt: LocalDateTime
    var updatedAt: LocalDateTime
}