package ice.maple.configserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.config.server.EnableConfigServer


@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
open class ConfigServerApplication

fun main(args:Array<String>){
    SpringApplication.run(ConfigServerApplication::class.java,*args)
}