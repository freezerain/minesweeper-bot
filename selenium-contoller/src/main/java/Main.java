import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        final String LOGIN = "freezerain";
        final String PASS = "@Sj5cySbQWTPP!C";
        System.setProperty("webdriver.chrome.driver",
                           "D:\\Soft\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");*/
        options.addArguments("--user-data-dir=D:\\Soft\\chromedriver_win32\\UserData");
        WebDriver driver = new ChromeDriver(options);
        //driver.manage().window().minimize();
        driver.manage().window().setSize(new Dimension(1024, 800));
        Scanner in = new Scanner(System.in);
        System.out.println("Main init: " + (System.currentTimeMillis()-start) + "ms.");
        SweeperSolver.startPlaying(driver, 3, 20, 5, 500);
        /*while(true){
            System.out.print("Difficulty(1-4) ?: ");
            int difficulty = in.nextInt();
            in.nextLine();
            System.out.println("Games to play?: ");
            int games = in.nextInt();
            in.nextLine();
            SweeperSolver.startPlaying(driver, difficulty, games, 3000, 5);
            System.out.println("Gaming session end. Enter /again/ or /quit/ :");
            String isRepeat = in.nextLine();
            if(isRepeat.equals("again")) continue;
            else break;
        }*/
        driver.quit();
    }
}
