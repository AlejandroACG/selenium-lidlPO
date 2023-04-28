import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        WebDriverManager.chromedriver().setup();
        this.driver = driver;
        this.wait = wait;
    }

    public void navigateToHomePage() {
        driver.get("https://www.lidl.es/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    public void navigateToCuberteria() {
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.className("pt21-category-ribbon-body"), By.linkText("Cocina")));
        driver.findElement(By.className("pt21-category-ribbon-body")).findElement(By.linkText("Cocina")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cubertería")));
        driver.findElement(By.linkText("Cubertería")).click();
    }

    public int countItems() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("product-grid-box-tile")));
        int count = driver.findElements(By.className("product-grid-box-tile")).size();

        return count;
    }

    public void searchFor(String input) {
        driver.findElement(By.id("mainsearch-input")).sendKeys(input);
        driver.findElement(By.className("search-bar-container-button")).click();
    }

    public void addFirstProductToBasket() {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("frontpage-product-teaser__addtocart")));
        driver.findElement(By.className("frontpage-product-teaser__addtocart")).click();
    }

    public int getNumberOnBasket() {
        int number = Integer.parseInt(driver.findElement(By.className("ua-basket")).findElement(By.className("status-area")).getText());
        return number;
    }

    public void navigateToBasketPage() {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("overlay-closer")));
        driver.findElement(By.className("overlay-closer")).click();
    }
}
