package vip.codemonkey.data.jpa.repository

import org.dbunit.ant.Operation
import org.dbunit.database.DatabaseConfig
import org.dbunit.database.DatabaseConnection
import org.dbunit.database.DefaultMetadataHandler
import org.dbunit.database.ForwardOnlyResultSetTableFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.sql.DriverManager
import javax.annotation.PostConstruct


/**
 *@author wang zhengtao
 *
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
abstract class BaseDataTestCase {
    protected val logger: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    @Autowired
    private lateinit var  env: Environment

    companion object{
        private var flag = false
    }

    @PostConstruct
    @Throws(Exception::class)
    fun postConstruct() {
        importSampleData()
    }

    @Throws(Exception::class)
    fun importSampleData() {
        env.getProperty("refreshDb") ?: return
        if (flag) return else flag = true
        val datasourceUrl = env.getProperty("spring.datasource.url")
        logger.info("db unit url is {}", datasourceUrl)
        val conn = DriverManager.getConnection(
            datasourceUrl!!,
            env.getProperty("spring.datasource.username"),
            env.getProperty("spring.datasource.password")
        )
        val connection = DatabaseConnection(conn)
        val config = connection.config
        config.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY, ForwardOnlyResultSetTableFactory())
        config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false)
        config.setProperty(
            DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
            Class.forName(env.getProperty("spring.dbunit.data-factory")).newInstance()
        )
        config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, DefaultMetadataHandler())

        val operation = Operation()
        operation.isTransaction = true
        operation.type = env.getProperty("spring.dbunit.operationType")
        operation.format = "flat"
        operation.src = File(env.getProperty("spring.dbunit.sample-file")!!)

        try {
            operation.execute(connection)
        } finally {
            connection.close()
        }
    }

}
