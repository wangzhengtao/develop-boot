package vip.codemonkey.data.jpa.convert

import vip.codemonkey.common.constant.Punctuations
import vip.codemonkey.common.utils.StringUtils
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class String2ListConverter:AttributeConverter<List<String>,String> {
    override fun convertToDatabaseColumn(attribute: List<String>) = StringUtils.list2String(attribute,Punctuations.COMMA)
    override fun convertToEntityAttribute(dbData: String?) = StringUtils.string2List(dbData,Punctuations.COMMA)
}