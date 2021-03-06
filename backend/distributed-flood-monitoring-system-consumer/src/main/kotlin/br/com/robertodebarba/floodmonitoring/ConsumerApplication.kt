package br.com.robertodebarba.floodmonitoring

import br.com.robertodebarba.floodmonitoring.consumer.FloodConsumer
import br.com.robertodebarba.floodmonitoring.consumer.RainFallConsumer
import br.com.robertodebarba.floodmonitoring.consumer.RiverLevelConsumer
import br.com.robertodebarba.floodmonitoring.core.entity.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import javax.annotation.PostConstruct


@SpringBootApplication
@ComponentScan(basePackages = arrayOf(
        "br.com.robertodebarba.floodmonitoring.consumer"
))
@EnableMongoRepositories(basePackages = arrayOf(
        "br.com.robertodebarba.floodmonitoring.core.entity"
))
class ConsumerApplication {

    @Value("\${consumerType}")
    private lateinit var consumerType: String

    @Autowired
    private lateinit var riverLevelConsumer: RiverLevelConsumer

    @Autowired
    private lateinit var rainFallConsumer: RainFallConsumer

    @Autowired
    private lateinit var floodConsumer: FloodConsumer

    @PostConstruct
    fun init() {
        consumerType.split(";").forEach {
            when (it) {
                "riverlevel" -> riverLevelConsumer.consume<RiverLevel, RiverLevelSHA1>()
                "rainfall" -> rainFallConsumer.consume<RainFall, RainFallSHA1>()
                "flood" -> floodConsumer.consume<Flood, FloodSHA1>()
            }
        }
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(ConsumerApplication::class.java, *args)
}