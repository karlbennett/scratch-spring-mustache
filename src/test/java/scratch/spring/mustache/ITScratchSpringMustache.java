package scratch.spring.mustache;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static scratch.spring.mustache.UserConstants.users;

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

        when(users.retrieve()).thenReturn(users());

        homePage.visit(baseUrl);

        assertThat("the correct users should be displayed.", homePage.users(), hasAllPartialUsers(users()));
    }

    private Matcher<? super List<User>> hasAllPartialUsers(final Iterable<User> expected) {

        return new TypeSafeMatcher<List<User>>() {

            @Override
            protected boolean matchesSafely(List<User> actual) {

                int count = 0;

                for (User user : expected) {

                    if (!contains(actual, user)) {
                        return false;
                    }

                    count++;
                }

                return actual.size() == count;
            }

            private boolean contains(Iterable<User> actual, User user) {

                for (User partialUser : actual) {

                    if (partialUser.getEmail().equals(user.getEmail()) &&
                            partialUser.getFirstName().equals(user.getFirstName()) &&
                            partialUser.getLastName().equals(user.getLastName())
                            ) {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(expected);
            }
        };
    }
}
