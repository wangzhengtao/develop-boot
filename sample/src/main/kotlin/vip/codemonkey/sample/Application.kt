package vip.codemonkey.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import vip.codemonkey.data.jpa.repository.CustomSimpleJpaRepository


/**
 *@author wang zhengtao
 *
 */
@SpringBootApplication(scanBasePackages = arrayOf("vip.codemonkey"))
@EnableJpaRepositories(repositoryBaseClass = CustomSimpleJpaRepository::class)
class Application

fun main(args: Array<String>) {
        runApplication<Application>()
}