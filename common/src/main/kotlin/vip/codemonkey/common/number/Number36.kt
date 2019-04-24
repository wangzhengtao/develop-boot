package vip.codemonkey.common.number

import java.util.*


/**
 * 三十六进制计算
 * @author wang zhengtao
 */
class Number36 {

     var DIGITS_UPPER: CharArray = charArrayOf( '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
     var DIGITS_LOWER: CharArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
     lateinit var DIGITS_MAP: IntArray
     val SINGLE_CHAR_MAX_VALUE = 36

    fun getInitNumber(length: Int): String {
        return String.format("%010d", 1)
    }

    fun nextNumber(number: String): String {
        val length = number.length
        val realNumber = restore(number)
        checkValues(realNumber)
        val resultNumber = add(realNumber, 1)
        val addZeroLength = length - resultNumber.length
        return String.format("%0" + addZeroLength + "d", 0) + resultNumber
    }


    fun int2Number(value: Int): String {
        if (value == 0) {
            return "0"
        }
        var number = value
        val buff = StringBuffer()
        var mod: Int
        while (number >= SINGLE_CHAR_MAX_VALUE) {
            mod = number % SINGLE_CHAR_MAX_VALUE
            val c = DIGITS_LOWER[mod]
            buff.append(c)

            number /= SINGLE_CHAR_MAX_VALUE
        }
        if (number > 0) {
            val c = DIGITS_LOWER[number]
            buff.append(c)
        }
        return buff.reverse().toString()
    }

    fun number2Int(number: String): Int {
        checkValues(number)
        var index = 0
        var sum = 0
        for (power in number.length - 1 downTo 0) {
            val c = number[index++]
            sum += (DIGITS_MAP[c.toInt()] * Math.pow(SINGLE_CHAR_MAX_VALUE.toDouble(), power.toDouble())).toInt()
        }
        return sum
    }

    fun add(num1: String, num2: Int): String {
        return add(num1, int2Number(num2))
    }

    fun add(num1: String, num2: String): String {
        val buff = StringBuilder()
        checkValues(num1, num2)
        var num1Index = num1.length - 1
        var num2Index = num2.length - 1
        var complement = 0
        while (num1Index >= 0 || num2Index >= 0) {
            val v1 = if (num1Index >= 0) DIGITS_MAP.get(num1.get(num1Index).toInt()) else 0
            val v2 = if (num2Index >= 0) DIGITS_MAP.get(num2.get(num2Index).toInt()) else 0
            val sum = v1 + v2 + complement

            complement = if (sum >= SINGLE_CHAR_MAX_VALUE) 1 else 0
            val value = sum % SINGLE_CHAR_MAX_VALUE
            buff.append(DIGITS_LOWER[value])
            --num1Index
            --num2Index
        }
        if (complement > 0) {
            buff.append('1')
        }
        return buff.reverse().toString()
    }

    private fun restore(number: String): String {
        val builder = StringBuilder()
        var append = false
        for (c in number.toCharArray()) {
            if (c != '0') {
                append = true
            }
            if (append) {
                builder.append(c)
            }
        }
        if (builder.length == 0) {
            builder.append('0')
        }
        return builder.toString()
    }

    private fun checkValues(vararg numbers: String) {
        for (number in numbers) {
            if (Objects.isNull(number) || number.length <= 0) {
                throw IllegalArgumentException(number + "is not a valid number36")
            }
            for (c in number.toCharArray()) {
                if (c != '0' && DIGITS_MAP[c.toInt()] == 0) {
                    throw IllegalArgumentException(number + "is not a valid number36 invalid char is " + c)
                }
            }
        }
    }

    init{
        DIGITS_MAP = IntArray(128)
        for (i in 0..DIGITS_UPPER.size - 1) {
            DIGITS_MAP.set(DIGITS_UPPER.get(i).toInt(),i)
            DIGITS_MAP.set(DIGITS_LOWER.get(i).toInt(),i)
        }
    }





}