import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /*final String LOGIN = "trapfreezerainitis";
        final String PASS = "trap@Sj5cySbQWTPP!Citis";*/
        System.setProperty("webdriver.chrome.driver",
                           "D:\\Soft\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");*/
        //options.addArguments("--user-data-dir=D:\\Soft\\chromedriver_win32\\UserData");
        options.addArguments("--ignore-certificate-errors");
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("178.63.17.151:3128");
        proxy.setSslProxy("178.63.17.151:3128");
        options.setCapability(CapabilityType.PROXY, proxy);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1800, 800));
        SweeperSolver.startPlaying(driver, 3, 20, 500, 200);
        driver.quit();
    }
}
