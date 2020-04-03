package ice.maple.helloserver

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class HelloServerApplicationTests {
    @Value("\${info.name}")
    private val name:String? = null
    @Value("\${info.sex}")
    private val sex:String? = null
    @Test
    fun contextLoads() {
        println(name)
        println(sex)
    }
}
