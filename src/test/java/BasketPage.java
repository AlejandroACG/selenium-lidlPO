import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class BasketPage {
    private final WebDriver driver;

    public BasketPage(WebDriver driverInUse) {
        WebDriverManager.chromedriver().setup();
        driver = driverInUse;
    }

    public final int countItems() {
        int totalAmount = 0;
        List<WebElement> selectElements = driver.findElements(By.cssSelector("select.submit-on-change"));
        List<WebElement> selectedOptions = new ArrayList<>();
        for (WebElement selectElement : selectElements) {
            List<WebElement> options = selectElement.findElements(By.tagName("option"));
            for (WebElement option : options) {
                if (option.isSelected()) {
                    selectedOptions.add(option);
                }
            }
        }
        for (WebElement selectedOption : selectedOptions) {
            int amount = Integer.parseInt(selectedOption.getText());
            totalAmount += amount;
        }
        return totalAmount;
    }
}
