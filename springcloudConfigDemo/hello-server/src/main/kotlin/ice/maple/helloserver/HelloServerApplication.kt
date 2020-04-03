package ice.maple.helloserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class HelloServerApplication

fun main(args:Array<String>){
    SpringApplication.run(HelloServerApplication::class.java,*args)
}