package dev.jefy.connectpro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * @author  Jôph Yamba
 */

@ConfigurationPropertiesScan
@EnableJpaRepositories
@SpringBootApplication
class ConnectPro

fun main(args: Array<String>) {
	runApplication<ConnectPro>(*args)
}
