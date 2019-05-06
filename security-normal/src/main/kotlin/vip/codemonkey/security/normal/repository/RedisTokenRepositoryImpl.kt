package vip.codemonkey.security.normal.repository

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import java.io.Serializable
import java.util.*

class RedisTokenRepositoryImpl(var redisTemplate: RedisTemplate<Any,Any>): PersistentTokenRepository {

    val REDIS_KEY = "remember_me_token"
    val opsForHash = redisTemplate.opsForHash<String,CustomPersistentRememberMeToken>()

    override fun updateToken(series: String, tokenValue: String, lastUsed: Date) {
        val token = getTokenForSeries(series)
        opsForHash.put(REDIS_KEY,series, CustomPersistentRememberMeToken(token?.username,series,tokenValue,Date()))
    }

    override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? {
        return opsForHash.get(REDIS_KEY,seriesId)?.toPersistentRememberMeToken()
    }

    override fun removeUserTokens(username: String?) {
        val token = opsForHash.entries(REDIS_KEY).values.filter { it.username.equals(username) }.last()
        opsForHash.delete(REDIS_KEY,token.series)
    }

    override fun createNewToken(token: PersistentRememberMeToken) {

        val current = opsForHash.get(REDIS_KEY,token.series)

        if(Objects.nonNull(current)) {
            throw DataIntegrityViolationException("Series Id '${token.series}' already exists!")
        }
        opsForHash.putIfAbsent(REDIS_KEY,token.series,CustomPersistentRememberMeToken.of(token))
    }
}

class CustomPersistentRememberMeToken(
    var username: String?,
    var series: String?,
    var tokenValue: String?,
    var date: Date?
) : Serializable{
    companion object{
        fun of(token:PersistentRememberMeToken):CustomPersistentRememberMeToken{
            return CustomPersistentRememberMeToken(token.username,token.series,token.tokenValue,token.date)
        }
    }

    fun toPersistentRememberMeToken():PersistentRememberMeToken{
        return PersistentRememberMeToken(username,series,tokenValue,date)
    }
}
