import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SweeperSolver {

    public static boolean solveMulti(WebDriver driver, int rows, int cols, int maxMoves,
                                     int endGameDelay, int moveDelay) throws InterruptedException {
        WebElement faceButton = new WebDriverWait(driver, 5).until(
                d -> d.findElement(By.id("top_area_face")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int moves = 0;
        js.executeScript(JSLibrary.createObserver());
        Cell[][] cellArr = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cellArr[i][j] = new Cell(i, j, true, false, false, 0);
        new WebDriverWait(driver, 5).until(d -> d.findElement(By.id("top_area_face"))
                .getAttribute("class")
                .contains("unpressed"));
        String faceClass = faceButton.getAttribute("class");
        js.executeScript(JSLibrary.getMultiClicker());
        while ((!faceClass.contains("face-lose")) && (!faceClass.contains("face-win")) &&
               moves <= maxMoves) {
            //noinspection unchecked
            List<String> elements = (ArrayList<String>) js.executeScript(
                    JSLibrary.getUpdatedAttributes());
            js.executeScript(JSLibrary.clearUpdates());
            for (int i = 0; i < elements.size(); i += 2){
                String[] webCellAttr = elements.get(i).split("_");
                int webX = Integer.parseInt(webCellAttr[2]);
                int webY = Integer.parseInt(webCellAttr[1]);
                if (i + 1 >= elements.size()) continue;
                cellArr[webX][webY].setParams(webX, webY, elements.get(i + 1));
            }
            List<String> moveList = new ArrayList<>(MsSolver.nextMoves(cellArr));
            if (moveList.isEmpty()) {
                System.out.println("Empty move!!1");
                return false;
            }
            int[] reversedMoveList = new int[moveList.size() * 2];
            for (int i = 0; i < moveList.size(); i += 2){
                String[] slitted = moveList.get(i).split(" ");
                reversedMoveList[i] = Integer.parseInt(slitted[1]);
                reversedMoveList[i + 1] = Integer.parseInt(slitted[0]);
            }
            js.executeScript(JSLibrary.getMultiClick(reversedMoveList));
            faceClass = faceButton.getAttribute("class");
            maxMoves++;
            sleep(moveDelay);
        }
        if(faceClass.contains("face-win")) sleep(60000);
        return faceClass.contains("face-win");
    }

    public static void startPlaying(WebDriver driver, int difficulty, int gamesToPlay,
                                    int sleepGames, int sleepMoves) throws InterruptedException {
        int row = difficulty == 1 ? 9 : difficulty == 2 || difficulty == 3 ? 16 : 32;
        int col = difficulty == 1 ? 9 : difficulty == 2 ? 16 : difficulty == 3 ? 30 : 32;
        if (row == 32) {
            System.out.println("This difficulty is not ready!");
            return;
        }
        driver.get("https://minesweeper.online/ru/start/" + difficulty);
        for (int i = 0; i < gamesToPlay; i++){
            System.out.println("Playing game №" + i);
            boolean isWin = SweeperSolver.solveMulti(driver, row, col, 500, sleepGames, sleepMoves);
            System.out.println("Game " + (isWin ? "Wined" : "Lost"));
            sleep(sleepGames);
            new WebDriverWait(driver, 5).until(d -> d.findElement(By.id("top_area_face"))).click();
        }
    }
}
