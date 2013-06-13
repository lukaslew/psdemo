package gui.page;

import models.Gender;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.id;

public class HomePage extends AbstractPage {
    public static final String PERSON_NAME_INPUT = "person-name-input";
    public static final String PERSON_NAME_INPUT_HELP = "person-name-input-help";
    public static final String PERSON_GENDER_RADIO_HELP = "person-gender-radio-help";
    public static final String PERSON_TABLE = "person-table";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String name, Gender gender) {
        input(id(PERSON_NAME_INPUT), name);
        click(id(String.format("person-gender-%s-radio", gender.name().toLowerCase())));
    }

    public boolean isTableDisplayed() {
        return driver.findElement(id(PERSON_TABLE)).isDisplayed();
    }

}
