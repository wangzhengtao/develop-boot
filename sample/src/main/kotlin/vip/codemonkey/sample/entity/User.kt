package vip.codemonkey.sample.entity

import vip.codemonkey.data.jpa.convert.String2ListConverter
import vip.codemonkey.data.jpa.entity.LongIdEntity
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @get:Column(nullable = false, length = 10,unique = true)
    var username:String = "",
    @get:Column(nullable = false)
    var password:String = "",
    @get:Column(columnDefinition = "TEXT")
    @get:Convert(converter = String2ListConverter::class)
    var permissions: MutableList<String> = mutableListOf()
): LongIdEntity(){

}