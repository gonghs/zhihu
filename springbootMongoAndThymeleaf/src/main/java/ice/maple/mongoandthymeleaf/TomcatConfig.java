package ice.maple.mongoandthymeleaf;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Configuration;


public class TomcatConfig implements TomcatConnectorCustomizer, TomcatContextCustomizer {
    @Override
    public void customize(Connector connector) {
        connector.setPort(9000);
    }

    @Override
    public void customize(Context context) {
        ErrorPage errorPage = new ErrorPage();
        errorPage.setErrorCode("404");
        errorPage.setLocation("/error/404");
        ErrorPage errorPage1 = new ErrorPage();
        errorPage1.setErrorCode("500");
        errorPage1.setLocation("/error/500");
        context.addErrorPage(errorPage);
        context.addErrorPage(errorPage1);
    }
}
