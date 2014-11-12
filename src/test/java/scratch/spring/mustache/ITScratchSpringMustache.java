package scratch.spring.mustache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import scratch.user.User;
import scratch.user.Users;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static scratch.spring.mustache.UserConstants.containsAll;
import static scratch.spring.mustache.UserConstants.userOne;
import static scratch.spring.mustache.UserConstants.userThree;
import static scratch.spring.mustache.UserConstants.userTwo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringMustacheServlet.class)
@WebAppConfiguration("classpath:")
@IntegrationTest({"server.port=0", "management.port=0"})
public class ITScratchSpringMustache {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private Users users;

    @Autowired
    private HomePage homePage;

    private String baseUrl;

    @Before
    public void setUp() {

        baseUrl = format("http://localhost:%d/mustache", port);
    }

    @Test
    public void I_can_view_the_home_page() {

        final List<User> users = asList(partialUser(userOne()), partialUser(userTwo()), partialUser(userThree()));

        when(this.users.retrieve()).thenReturn(users);

        homePage.visit(baseUrl);

        assertThat("the correct users should be displayed.", homePage.users(), containsAll(users));
    }

    private static User partialUser(User user) {

        final User partialUser = new User(user);
        partialUser.setId(null);
        partialUser.setPhoneNumber(null);
        partialUser.setAddress(null);

        return partialUser;
    }
}
