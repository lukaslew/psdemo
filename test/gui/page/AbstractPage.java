package gui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.openqa.selenium.By.tagName;

public class AbstractPage {
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isTextExist(By locator, String text) {
        WebElement element = driver.findElement(locator);
        String content = element != null ? element.getText() : "";

        return content.equalsIgnoreCase(text);
    }

    public void input(By locator, CharSequence charSequence) {
        WebElement element = driver.findElement(locator);

        if (element != null) {
            element.sendKeys(charSequence);
        }
    }

    public void click(By locator) {
        WebElement element = driver.findElement(locator);

        if (element != null) {
            element.click();
        }
    }

    public List<String> getTableRow(By locator, int idx) {
        WebElement element = driver.findElement(locator);
        List<String> list = new ArrayList<>();

        if (element != null) {
            List<WebElement> rows = element.findElement(tagName("tbody")).findElements(tagName("tr"));

            if (isNotEmpty(rows)) {
                WebElement first = rows.get(idx);
                List<WebElement> cells = first.findElements(tagName("td"));

                if (isNotEmpty(cells)) {
                    for (int i = 0; i < cells.size() - 1; ++i) {
                        list.add(cells.get(i).getText());
                    }
                }
            }
        }

        return list;
    }
}
