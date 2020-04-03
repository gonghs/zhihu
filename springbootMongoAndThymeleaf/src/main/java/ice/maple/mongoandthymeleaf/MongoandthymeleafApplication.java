package ice.maple.mongoandthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
public class MongoandthymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoandthymeleafApplication.class, args);
    }



    @Bean
    public TomcatServletWebServerFactory initFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
//        tomcatServletWebServerFactory.addConnectorCustomizers(new TomcatConfig());
//        tomcatServletWebServerFactory.addContextCustomizers(new TomcatConfig());
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
        tomcatServletWebServerFactory.addErrorPages(error404Page, error500Page);
        return tomcatServletWebServerFactory;
    }
}
