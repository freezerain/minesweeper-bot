import java.util.Arrays;
import java.util.Random;

public class MinesweeperGenerator {
    public static int[][] generateField(int rows, int cols, int mines) {
        if (mines >= rows * cols) return null;
        int[][] field = new int[rows][cols];
        Random random = new Random();
        while (mines > 0) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            if (field[row][col] != -9) {
                field[row][col] = -9;
                mines--;
            }
        }
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (field[row][col] != -9) continue;
                for (int rowNeighbor = Math.max(0, row - 1); rowNeighbor < Math.min(rows, row + 2);
                     rowNeighbor++){
                    for (int colNeighbor = Math.max(0, col - 1);
                         colNeighbor < Math.min(cols, col + 2); colNeighbor++){
                        if (field[rowNeighbor][colNeighbor] != -9)
                            field[rowNeighbor][colNeighbor] = field[rowNeighbor][colNeighbor] + 1;
                    }
                }
            }
        }
        return field;
    }
    
    public static Cell[][] generate(int rows, int cols, int mines){
        if(rows<2 || cols <2 || mines<1 || rows*cols<= mines) return null;
        Cell[][] field = new Cell[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                field[i][j] = new Cell(i, j, true, false, false, 0);
            }
            
        }
        
        while (mines > 0) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            if (!field[row][col].hasMine) {
                field[row][col].hasMine = true;
                mines--;
            }
        }
        
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (!field[row][col].hasMine) continue;
                for (int rowNeighbor = Math.max(0, row - 1); rowNeighbor < Math.min(rows, row + 2);
                     rowNeighbor++){
                    for (int colNeighbor = Math.max(0, col - 1);
                         colNeighbor < Math.min(cols, col + 2); colNeighbor++){
                        if (!field[rowNeighbor][colNeighbor].hasMine)
                            field[rowNeighbor][colNeighbor].mineCounter = field[rowNeighbor][colNeighbor].mineCounter+1;
                    }
                }
            }
        }
        return field;
    }

    public static void printField(int[][] field) {
        System.out.println("\n\t" + Arrays.deepToString(field).
                replaceAll("\\s|\\[|\\](?!,)", "").
                replace(",", "\t").
                replace("]", "\n"));
    }

    public static void printField(Cell[][] field) {
        System.out.println("\n\t" + Arrays.deepToString(field).
                replaceAll("\\s|\\[|\\](?!,)", "").
                replace(",", "\t").
                replace("]", "\n"));
    }
}
