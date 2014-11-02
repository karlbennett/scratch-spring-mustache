package scratch.spring.mustache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringMustacheServlet.class)
@WebAppConfiguration("classpath:")
public class ScratchSpringMustacheServletTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void I_can_inject_the_application_context() {

        assertNotNull("the application context should have been injected.", context);
    }
}
