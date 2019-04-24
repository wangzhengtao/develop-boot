package vip.codemonkey.common.extension

import java.time.LocalDateTime

/**
 *@author wang zhengtao
 *
 */
inline fun Long.availableThen(block: Long.() -> Unit): Long {
    if (this != 0L) block()
    return this
}

inline fun String.notBlankThen(block: String.() -> Unit): String {
    if (isNotBlank()) block()
    return this
}

inline fun <T> List<T>.notEmptyThen(block: () -> Unit): List<T> {
    if (!this.isEmpty())
        block()
    return this
}

inline  fun LocalDateTime.notMinThen(block:() -> Unit):LocalDateTime{
    if(!this.isEqual(LocalDateTime.MIN)) block()
    return this
}

