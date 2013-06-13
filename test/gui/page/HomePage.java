package gui.page;

import models.Gender;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;

public class HomePage extends AbstractPage {
    public static final String PERSON_NAME_INPUT = "person-name-input";
    public static final String PERSON_NAME_INPUT_HELP = "person-name-input-help";
    public static final String PERSON_GENDER_RADIO_HELP = "person-gender-radio-help";
    public static final String PERSON_TABLE = "person-table";
    public static final String PERSON_FORM_SUBMIT = "person-form-submit";

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

    public void editPerson(int idx) {
        WebElement element = driver.findElement(id(PERSON_TABLE));

        if (element != null) {
            List<WebElement> rows = element.findElement(tagName("tbody")).findElements(tagName("tr"));

            if (isNotEmpty(rows)) {
                WebElement row = rows.get(idx);
                List<WebElement> cells = row.findElements(tagName("td"));

                if (isNotEmpty(cells)) {
                    WebElement last = cells.get(cells.size() - 1);
                    WebElement edit = last.findElement(className("btn-warning"));

                    if (edit != null) {
                        edit.click();
                    }
                }
            }
        }
    }
}
