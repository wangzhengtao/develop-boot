package vip.codemonkey.security.core.properties

class ImageCodeProperties: SmsCodeProperties() {
    var width:Int = 67
    var height:Int = 23

    init {
        length = 4
    }
}