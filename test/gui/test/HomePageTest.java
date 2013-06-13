package gui.test;

import gui.page.HomePage;
import models.Gender;
import models.Person;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import play.libs.F.Callback;
import play.test.TestBrowser;

import java.util.List;

import static gui.page.HomePage.PERSON_GENDER_RADIO_HELP;
import static gui.page.HomePage.PERSON_NAME_INPUT_HELP;
import static gui.page.HomePage.PERSON_TABLE;
import static org.fest.assertions.Assertions.assertThat;
import static org.openqa.selenium.By.id;
import static play.test.Helpers.FIREFOX;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class HomePageTest {
    private static final String PERSON_NAME_IS_REQUIRED = "Person name is required";
    private static final String GENDER_IS_REQUIRED = "Gender is required";

    private static final Person LUKE = new Person("Luke", Gender.MALE);

    private static final int ONE_MINUTE = 60;

    @Test
    public void shouldAddPerson() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) throws InterruptedException {
                browser.goTo("http://localhost:3333");

                HomePage homePage = new HomePage(browser.getDriver());

                assertThat(homePage.isTextExist(id(PERSON_NAME_INPUT_HELP), PERSON_NAME_IS_REQUIRED)).isTrue();
                assertThat(homePage.isTextExist(id(PERSON_GENDER_RADIO_HELP), GENDER_IS_REQUIRED)).isTrue();
                assertThat(homePage.isTableDisplayed()).isFalse();

                homePage.fillForm(LUKE.getName(), LUKE.getGender());

                assertThat(homePage.isTextExist(id(PERSON_NAME_INPUT_HELP), PERSON_NAME_IS_REQUIRED)).isFalse();
                assertThat(homePage.isTextExist(id(PERSON_GENDER_RADIO_HELP), GENDER_IS_REQUIRED)).isFalse();

                homePage.click(id("person-form-submit"));
                new WebDriverWait(browser.getDriver(), ONE_MINUTE).until(
                        ExpectedConditions.visibilityOfElementLocated(id(PERSON_TABLE))
                );

                assertThat(homePage.isTableDisplayed()).isTrue();

                List<String> row = homePage.getTableRow(id(PERSON_TABLE), 0);

                assertThat(row).hasSize(2);
                assertThat(row.get(0)).isEqualTo(LUKE.getName());
                assertThat(row.get(1)).isEqualToIgnoringCase(LUKE.getGender().name());
            }
        });
    }
  
}
