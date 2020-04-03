package ice.maple.springbootossupload;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringbootConfig implements WebMvcConfigurer{
    //设置默认跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
//        registry.addViewController("/").setViewName("vueUploader");
//        registry.addViewController("/index").setViewName("redirect:/index/getAttachs");
//        registry.addViewController("/user").setViewName("/index");
    }

}
