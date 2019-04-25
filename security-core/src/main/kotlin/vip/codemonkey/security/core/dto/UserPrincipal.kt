package vip.codemonkey.security.core.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.io.Serializable

class UserPrincipal<T : GrantedAuthority>(var id:Long, username:String, password:String, authorities:MutableCollection<T>):
    User(username,password,authorities), Serializable {
    companion object{

    }
    val attribute:MutableMap<String,Any> = mutableMapOf()

}