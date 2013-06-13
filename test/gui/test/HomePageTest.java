package gui.test;

import com.google.common.base.Predicate;
import gui.page.HomePage;
import models.Gender;
import models.Person;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import play.libs.F.Callback;
import play.test.TestBrowser;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static gui.page.HomePage.PERSON_FORM_SUBMIT;
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

    private static final Person DARTH_VADER = new Person("Darth Vader", Gender.MALE);
    private static final Person LUKE = new Person("Luke", Gender.MALE);
    private static final Person LEIA = new Person("Leia", Gender.FEMALE);
    private static final Person HAN_SOLO = new Person("Han Solo", Gender.MALE);

    private static final int ONE_MINUTE = 60;
    private static final String URL = "http://localhost:3333";

    @Test
    public void shouldAddPerson() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) throws Throwable {
                browser.goTo(URL);

                HomePage homePage = new HomePage(browser.getDriver());

                assertThat(homePage.isTextExist(id(PERSON_NAME_INPUT_HELP), PERSON_NAME_IS_REQUIRED)).isTrue();
                assertThat(homePage.isTextExist(id(PERSON_GENDER_RADIO_HELP), GENDER_IS_REQUIRED)).isTrue();
                assertThat(homePage.isTableDisplayed()).isFalse();

                homePage.fillForm(LUKE.getName(), LUKE.getGender());

                assertThat(homePage.isTextExist(id(PERSON_NAME_INPUT_HELP), PERSON_NAME_IS_REQUIRED)).isFalse();
                assertThat(homePage.isTextExist(id(PERSON_GENDER_RADIO_HELP), GENDER_IS_REQUIRED)).isFalse();

                homePage.click(id(PERSON_FORM_SUBMIT));
                new WebDriverWait(browser.getDriver(), ONE_MINUTE).until(
                        ExpectedConditions.visibilityOfElementLocated(id(PERSON_TABLE))
                );

                assertThat(homePage.isTableDisplayed()).isTrue();

                assertTableRow(homePage, 0, LUKE);
            }
        });
    }

    @Test
    public void shouldEditPerson() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX, new Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) throws Throwable {
                browser.goTo(URL);

                final HomePage homePage = new HomePage(browser.getDriver());
                WebDriverWait wait = new WebDriverWait(browser.getDriver(), ONE_MINUTE);
                List<Person> people = Arrays.asList(DARTH_VADER, LUKE, LEIA);

                for (int i = 0; i < people.size(); i++) {
                    Person p = people.get(i);

                    homePage.fillForm(p.getName(), p.getGender());
                    homePage.click(id(PERSON_FORM_SUBMIT));

                    wait.until(new TableRowCountPredicate(homePage, i + 1));
                }

                assertThat(homePage.isTableDisplayed()).isTrue();
                assertThat(homePage.getTableRowCount(id(PERSON_TABLE))).isEqualTo(3);

                for (int i = 0; i < people.size(); ++i) {
                    assertTableRow(homePage, i, people.get(i));
                }

                homePage.editPerson(2);

                homePage.fillForm(HAN_SOLO.getName(), HAN_SOLO.getGender());
                homePage.click(id(PERSON_FORM_SUBMIT));

                assertThat(homePage.getTableRowCount(id(PERSON_TABLE))).isEqualTo(3);

                people = Arrays.asList(DARTH_VADER, LUKE, HAN_SOLO);
                for (int i = 0; i < people.size(); ++i) {
                    assertTableRow(homePage, i, people.get(i));
                }
            }
        });
    }

    private void assertTableRow(HomePage homePage, int idx, Person p) {
        List<String> row = homePage.getTableRow(id(PERSON_TABLE), idx);

        assertThat(row).hasSize(2);
        assertThat(row.get(0)).isEqualTo(p.getName());
        assertThat(row.get(1)).isEqualToIgnoringCase(p.getGender().name());
    }

    private class TableRowCountPredicate implements Predicate<WebDriver> {
        private HomePage homePage;
        private int count;

        private TableRowCountPredicate(HomePage homePage, int count) {
            this.homePage = homePage;
            this.count = count;
        }

        @Override
        public boolean apply(@Nullable WebDriver webDriver) {
            return homePage.getTableRowCount(id(PERSON_TABLE)) == count;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            return true;
        }
    }

}
