import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LidlPOTest {
    private static WebDriver driver;
    private static HomePage home;

    @BeforeAll
    public static void setUp() {
        // Cargamos el driver, arrancamos el navegador, maximizamos la ventana y aceptamos las cookies.
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        home = new HomePage(driver, wait);

        driver.manage().window().maximize();
        home.navigateToHomePage();
        driver.findElement(By.className("cookie-alert-extended-button")).click();
    }

    @AfterEach
    public void reset() {
        home.navigateToHomePage();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void test1() {
        // Navegamos hasta la categoría "Cubertería".
        home.navigateToCuberteria();

        // Comprobamos que carga los 9 resultados que muestra por pantalla.
        int count = home.countItems();
        Assertions.assertEquals(9, count);
    }

    @Test
    public void test2() {
        // Localizamos y empleamos la barra de búsqueda.
        home.searchFor("Muebles");
        // Comprobamos el título del resultado.
        Assertions.assertEquals("Resultado de búsqueda | Lidl", driver.getTitle());
    }

    @Test
    public void test3() {
        // Usamos la barra de búsqueda para buscar productos.
        home.searchFor("gaming");
        // Añadimos un producto al azar al carrito.
        home.addFirstProductToBasket();

        // Imagino que aquí lo suyo es testear si el producto se ha añadido correctamente al carrito.
        // La manera más fácil sería obtener el dato del numerito pequeño que aparece sobre el carrito:
        int numberOnBasket = home.getNumberOnBasket();
        Assertions.assertEquals(1, numberOnBasket);

        // Pero he preferido escribir también un método alternativo más complicado pero, en mi opinión, quizás más fiable.
        // La siguiente parte del test abre la página del carrito y suma la cantidad de productos que hay en él.
        // El assert comprueba que hay 1 producto en el carrito (la cantidad que hemos añadido antes),
        // pero con este código podríamos comprobar también otras cantidades solo cambiando el valor expected del assert.

        BasketPage basket = new BasketPage(driver);
        home.navigateToBasketPage();

        int count = basket.countItems();
        Assertions.assertEquals(1, count);
    }
}
