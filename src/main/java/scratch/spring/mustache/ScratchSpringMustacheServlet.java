package scratch.spring.mustache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;
import scratch.ScratchSpringBootServlet;

/**
 * This is the bootstrap that starts up the whole Spring Boot framework.
 *
 * @author Karl Bennett
 */
@Configuration
@Import(ScratchSpringBootServlet.class)
public class ScratchSpringMustacheServlet {

    /**
     * Configure the servlet context for this webapp. All it's endpoints will fall under {@code /mustache}.
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.addUrlMappings("/mustache/*");
        return registration;
    }

    /**
     * The standard Spring Boot main method. It is used when the packaged war is executed with {@code java -jar}
     */
    public static void main(String[] args) {

        SpringApplication.run(ScratchSpringMustacheServlet.class, args);
    }
}
