package vip.codemonkey.sample.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import vip.codemonkey.common.constant.Punctuations
import vip.codemonkey.common.utils.StringUtils
import vip.codemonkey.sample.repository.UserRepository
import vip.codemonkey.security.core.dto.UserPrincipal

@Service
class UserDetailServiceImpl: UserDetailsService {
    val logger = LoggerFactory.getLogger(UserDetailServiceImpl::class.java)
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String): UserDetails {
        val userOptional = userRepository.findByUsername(username)
        logger.info("user is present {}",userOptional.isPresent)
        val user = if(userOptional.isPresent) userOptional.get() else throw UsernameNotFoundException("user $username not found ") as Throwable
        return of(user)
    }

    fun of(user:vip.codemonkey.sample.entity.User):UserPrincipal<GrantedAuthority>{
        return UserPrincipal(user.id,user.username,user.password, AuthorityUtils.commaSeparatedStringToAuthorityList(
            StringUtils.list2String(user.permissions,Punctuations.COMMA)))
    }

}

