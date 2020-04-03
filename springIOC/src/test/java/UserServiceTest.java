
import ice.maple.service.impl.IUserService;
import ice.maple.service.impl.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-config.xml")
@ContextConfiguration(classes = SpringConfig.class)
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Test
    public void getUsers() {
        userService.getUsers().forEach(n -> {
            System.out.println(n.getName());
            System.out.println(n.getSex());
        });
    }
}