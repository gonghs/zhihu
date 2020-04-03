package ice.maple.eurekaserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer


@SpringBootApplication
@EnableEurekaServer
open class EurekaServerApplication

fun main(args:Array<String>){
    SpringApplication.run(EurekaServerApplication::class.java,*args)
}