package dev.jefy.connectpro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class ConnectproApplication

fun main(args: Array<String>) {
	runApplication<ConnectproApplication>(*args)
}
