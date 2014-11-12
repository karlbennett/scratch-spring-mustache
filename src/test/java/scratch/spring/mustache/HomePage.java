package scratch.spring.mustache;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Component
public class HomePage {

    @Autowired
    private WebDriver driver;

    public void visit(String baseUrl) {

        driver.get(baseUrl + "/view/users");

        assertEquals("The title of the current page should be correct.", "All Users", driver.getTitle());
    }

    public List<User> users() {

        final List<User> users = new ArrayList<>();

        for (WebElement element : driver.findElements(By.className("user"))) {

            final String email = element.findElement(By.className("email")).getText();
            final String firstName = element.findElement(By.className("firstName")).getText();
            final String lastName = element.findElement(By.className("lastName")).getText();

            users.add(new User(email, firstName, lastName, null, null));
        }

        return users;
    }
}
