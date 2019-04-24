package vip.codemonkey.common.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


/**
 *@author wang zhengtao
 *
 */
class DateUtil {
    companion object{
        @JvmStatic
        fun toLocalDateTime(date: Date): LocalDateTime {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        }

        @JvmStatic
        fun toLocalDate(date: Date): LocalDate {
            return toLocalDateTime(date).toLocalDate()
        }

        @JvmStatic
        fun toLocalTime(date: Date): LocalTime {
            return toLocalDateTime(date).toLocalTime()
        }

        @JvmStatic
        fun fromLocalDate(date: LocalDate): Date {
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
        }

        @JvmStatic
        fun fromLocalDateTime(dateTime: LocalDateTime): Date {
            return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
        }

        @JvmStatic
        fun fromLocalTime(time: LocalTime): Date {
            return fromLocalDateTime(LocalDateTime.of(LocalDate.now(), time))
        }

        @JvmStatic
        fun plusNanos(date: Date, nanos: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusNanos(nanos))
        }

        @JvmStatic
        fun plusSeconds(date: Date, seconds: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusSeconds(seconds))
        }

        @JvmStatic
        fun plusMinutes(date: Date, minutes: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusMinutes(minutes))
        }

        @JvmStatic
        fun plusHours(date: Date, hours: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusHours(hours))
        }

        @JvmStatic
        fun plusDays(date: Date, daysToAdd: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusDays(daysToAdd))
        }

        @JvmStatic
        fun plusWeeks(date: Date, weeks: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusWeeks(weeks))
        }

        @JvmStatic
        fun plusMonths(date: Date, months: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusMonths(months))
        }

        @JvmStatic
        fun plusYears(date: Date, years: Long): Date {
            val localDateTime = toLocalDateTime(date)
            return fromLocalDateTime(localDateTime.plusYears(years))
        }
    }
}