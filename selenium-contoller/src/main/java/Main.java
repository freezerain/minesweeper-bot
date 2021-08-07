import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                           "D:\\Soft\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://minesweeper.online/ru/game/800460192");
        sleep(2000);
        WebElement selectMediumLevelButton = driver.findElement(By.id("level_select_2"));
        selectMediumLevelButton.click();
        sleep(2000);
        driver.findElement(By.id("level_select_1")).click();
        WebElement cell_1_0 = driver.findElement(By.id("cell_1_0"));
        Actions actionProvider = new Actions(driver);
        actionProvider.click(cell_1_0).build().perform();
        
    }
}
