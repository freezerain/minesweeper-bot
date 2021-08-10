import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Element;

import javax.lang.model.util.Elements;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SweeperSolver {
    public static boolean solve(WebDriver driver, int rows, int cols, int maxMoves, int delay) throws InterruptedException {
        long start = System.currentTimeMillis();
        Actions actionProvider = new Actions(driver);
        WebElement faceButton = new WebDriverWait(driver, 5)
                .until(d -> d.findElement(By.id("top_area_face")));
        String faceClass = faceButton.getAttribute("class");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int moves = 0;
        sleep(500);
        System.out.println("solve init: " + (System.currentTimeMillis()-start) + "ms.");
        while((!faceClass.contains("face-lose")) && (!faceClass.contains("face-win")) && moves<= maxMoves){
            start = System.currentTimeMillis();
            List<String> elements = (ArrayList<String>) js.executeScript("var result = [];\n" +
                                                                         "var elements = document.getElementsByClassName(\"cell\");\n" +
                                                                         "for (var i = 0; i < elements.length; i++){\n" +
                                                                         "     result.push(elements[i].getAttribute(\"data-x\") + \"/\" + elements[i].getAttribute(\"data-y\") + \"/\"+ elements[i].getAttribute(\"class\"));\n" +
                                                                         "}\n" + "return result");  
            System.out.println("solve.executeScript: " + (System.currentTimeMillis()-start) + "ms.");
            start = System.currentTimeMillis();
            Cell[][] cellArr = new Cell[rows][cols];
            for (String webCell : elements) {
                String[] webCellAttr = webCell.split("/");
                String classAtt = webCellAttr[2];
                int webX = Integer.parseInt(webCellAttr[0]);
                int webY = Integer.parseInt(webCellAttr[1]);
                int value = 0;
                if(classAtt.contains("hd_type")) value = Character.getNumericValue(classAtt.charAt(classAtt.indexOf("hd_type")+7));
                cellArr[webX][webY] = new Cell(webX, webY, classAtt.contains("closed"), false, classAtt.contains("flag"),value );
            }
            
            System.out.println("solve.for parser: " + (System.currentTimeMillis()-start) + "ms.");
            start = System.currentTimeMillis();
            String nm = MsSolver.nextMove(cellArr);
            if(nm.isEmpty()){
                System.out.println("Empty move!!1");
                return false;
            }
            String[] nextMove = nm.split(" ");
            WebElement cellToAct = driver.findElement(
                    By.id("cell_" + nextMove[0] + "_" + nextMove[1]));
            if(nextMove[2].equals("lclick")) actionProvider.click(cellToAct).build().perform();
            else actionProvider.contextClick(cellToAct).build().perform();
            faceClass = faceButton.getAttribute("class");
            maxMoves++;
            sleep(delay);
            System.out.println("solve.rest: " + (System.currentTimeMillis()-start) + "ms.");
            start = System.currentTimeMillis();
        }
        System.out.println("solve.return: " + (System.currentTimeMillis()-start) + "ms.");
        return faceClass.contains("face-win");
    }
    
    public static boolean solveMulti(WebDriver driver, int rows, int cols, int maxMoves, int delay) throws InterruptedException {
        long start = System.currentTimeMillis();
        Actions actionProvider = new Actions(driver);
        WebElement faceButton = new WebDriverWait(driver, 5)
                .until(d -> d.findElement(By.id("top_area_face")));
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int moves = 0;
        long elementsTime = System.currentTimeMillis();
        List<List<Object>> eleList= (List<List<Object>>)js.executeScript(JSLibrary.getElementsWithCoordinates());
        RemoteWebElement[][] elementArr = new RemoteWebElement[rows][cols];
        for (List<Object> e : eleList) {
            elementArr[Integer.parseInt((String)e.get(1))][Integer.parseInt((String)e.get(0))] = (RemoteWebElement)e.get(2);
        }
        System.out.println("Element time:" + (System.currentTimeMillis() - elementsTime) + "ms.");
        js.executeScript(JSLibrary.createObserver());
        
        Cell[][] cellArr = new Cell[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                cellArr[i][j] = new Cell(i, j, true, false, false, 0);
            }
        }
        sleep(500);
        String faceClass = faceButton.getAttribute("class");
        boolean[][] isVisited = new boolean[rows][cols];
        System.out.println("solve init: " + (System.currentTimeMillis()-start) + "ms.");
        
        js.executeScript(JSLibrary.getMultiClicker());
        
        //Gaming loop
        while((!faceClass.contains("face-lose")) && (!faceClass.contains("face-win")) && moves<= maxMoves){
            start = System.currentTimeMillis();
            List<String> elements = (ArrayList<String>) js.executeScript(JSLibrary.getUpdatedAttributes());
            js.executeScript(JSLibrary.clearUpdates());
            System.out.println("solve.executeScript: " + (System.currentTimeMillis()-start) + "ms.");
            start = System.currentTimeMillis();
            
            for (int i = 0; i < elements.size(); i+=2){
                String[] webCellAttr = elements.get(i).split("_");
                int webX = Integer.parseInt(webCellAttr[2]);
                int webY = Integer.parseInt(webCellAttr[1]);
               /* if(isVisited[webX][webY]){ continue;}
                isVisited[webX][webY] = true;*/
                if(i+1>= elements.size()) continue;
                cellArr[webX][webY].setParams(webX, webY, elements.get(i+1));
            }
            System.out.println("solve.for parser: " + (System.currentTimeMillis()-start) + "ms.");
            start = System.currentTimeMillis();
            
            List<String> moveList = MsSolver.nextMoves(cellArr);
            if(moveList.isEmpty()){
                System.out.println("Empty move!!1");
                return false;
            }
            System.out.println(moveList);
            
            int[] reversedMoveList = new int[moveList.size()*2];
            for (int i = 0; i< moveList.size(); i+=2) {
                String[] splitted = moveList.get(i).split(" ");
                reversedMoveList[i] = Integer.parseInt(splitted[1]);
                reversedMoveList[i+1] = Integer.parseInt(splitted[0]);
            }
            Timer jsClickTimer = new Timer("solver.JSClicks");
            js.executeScript(JSLibrary.getMultiClick(reversedMoveList));
            jsClickTimer.printTime();
/*            long clickAvg = 0;
            for(String s : moveList) {
                long clickStart = System.currentTimeMillis();
                String[] nextMove = s.split(" ");
                if(nextMove[2].equals("lclick")) actionProvider.click(elementArr[Integer.parseInt(nextMove[0])][Integer.parseInt(nextMove[1])]);
                else actionProvider.contextClick(elementArr[Integer.parseInt(nextMove[0])][Integer.parseInt(nextMove[1])]);
                clickAvg+= System.currentTimeMillis()-clickStart;
            }
            System.out.println("Click avg: " + (clickAvg/moveList.size()) + "ms.");
            Timer timeCliclPerform = new Timer("solver.loop.Click.Performe");
            actionProvider.build().perform();
            timeCliclPerform.printTime();
            
            */
            
            Timer timeRest = new Timer("solver.loop.end");
            faceClass = faceButton.getAttribute("class");
            maxMoves++;
            sleep(delay);
            start = System.currentTimeMillis();
            timeRest.printTime();
        }
        System.out.println("solve.return: " + (System.currentTimeMillis()-start) + "ms.");
        //if(faceClass.contains("face-lose")) sleep(10000);
        return faceClass.contains("face-win");
        
    }
    
    
    
    public static void startPlaying(WebDriver driver, int difficulty, int gamesToPlay, int sleepGames, int sleepMoves) throws InterruptedException {
        long start = System.currentTimeMillis();
        int row = difficulty==1? 9: difficulty==2 || difficulty==3? 16: 32;
        int col = difficulty==1? 9: difficulty==2? 16: difficulty==3? 30: 32;
        if(row==32){
            System.out.println("This difficulty is not ready!");
            return;
        }
        driver.get("https://minesweeper.online/ru/start/" + difficulty);
        WebElement faceButton = new WebDriverWait(driver, 5)
                .until(d -> d.findElement(By.id("top_area_face")));
        System.out.println("startPlaying init: " + (System.currentTimeMillis()-start) + "ms.");
        long min = Long.MAX_VALUE;
        long max = 0;
        long totalAvg = 0;
        for (int i = 0; i < gamesToPlay; i++){
            long l = System.currentTimeMillis();
            System.out.println("Playing game №" + i);
            boolean isWin = SweeperSolver.solveMulti(driver, row, col, 500, sleepMoves);
            System.out.println("Game " + (isWin? "Winned" : "Lost"));
            sleep(sleepGames);
            faceButton.click();
            long l1 = System.currentTimeMillis() - l;
            min = Math.min(l1, min);
            max = Math.max(l1, max);
            totalAvg +=l1;
        }
        System.out.println("startPlaying stat min: " + min + "ms. max: " + max + "ms. avg: " + (totalAvg/gamesToPlay) + "ms.");
        System.out.println("startPLaying end: " + (System.currentTimeMillis()-start) + "ms.");
    }
}
