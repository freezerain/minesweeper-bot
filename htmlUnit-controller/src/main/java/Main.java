import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME);) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            HtmlPage page = webClient.getPage("https://minesweeper.online/ru/start/2");
            webClient.waitForBackgroundJavaScript(1000);
            webClient.waitForBackgroundJavaScriptStartingBefore(1000);
            sleep(1000);
            List<Object> byXPath = page.getByXPath("//div[contains(@class, 'cell')]");
            List<Object> byXPath1 = page.getByXPath("//*[@id=\"A43\"]");
            List<DomElement> cell = page.getElementsById("cell");
            List<DomElement> cell_0_0 = page.getElementsById("cell_0_0");
            System.out.println(byXPath);
            System.out.println(byXPath1);
            System.out.println(cell);
            System.out.println(cell_0_0);
            webClient.close();
        }catch (Exception e) {
            
        }
    }
}
