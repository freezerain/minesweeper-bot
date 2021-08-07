public class MsSolver {
    public static String nextMove(int[][] field) {
        String nextMove = "";
        for (int row = 0; row < field.length; row++){
            for (int col = 0; col < field[row].length; col++){
                if (field[row][col] != -9 && field[row][col] != 0 && field[row][col] != -1 &&
                    field[row][col] != 9) {

                    int value = field[row][col];

                    int lastRow = -1;
                    int lastCol = -1;
                    int closedCells = 0;
                    for (int rowNeighbor = Math.max(0, row - 1);
                         rowNeighbor < Math.min(field.length, row + 2); rowNeighbor++){
                        for (int colNeighbor = Math.max(0, col - 1);
                             colNeighbor < Math.min(field[rowNeighbor].length, col + 2);
                             colNeighbor++){
                            if (field[rowNeighbor][colNeighbor] == 0) continue;
                            if (field[rowNeighbor][colNeighbor] == 9) {
                                value--;
                                continue;
                            }
                            if (field[rowNeighbor][colNeighbor] == -1 ||
                                field[rowNeighbor][colNeighbor] == -9) {
                                //value--;
                                lastRow = rowNeighbor;
                                lastCol = colNeighbor;
                                closedCells++;
                            }
                        }
                    }
                    if (value == 0 && closedCells > 0) return lastRow + " " + lastCol + " lclick";
                    if (value == closedCells && value > 0) {
                        return lastRow + " " + lastCol + " rclick";
                    }
                } else if (field[row][col] == -1 && nextMove.isEmpty())
                    nextMove = row + " " + col + " lclick";
            }
        }
        return nextMove;
    }

    public static String nextMove(Cell[][] field) {
        String nextMove = "";
        for (int row = 0; row < field.length; row++){
            for (int col = 0; col < field[row].length; col++){
                if ((!field[row][col].isClosed) && field[row][col].mineCounter > 0) {

                    Cell cell = field[row][col];
                    int flags = 0;
                    Cell lastCell = null;
                    int closedCells = 0;
                    for (int rowNeighbor = Math.max(0, row - 1);
                         rowNeighbor < Math.min(field.length, row + 2); rowNeighbor++){
                        for (int colNeighbor = Math.max(0, col - 1);
                             colNeighbor < Math.min(field[rowNeighbor].length, col + 2);
                             colNeighbor++){
                            if ((!field[rowNeighbor][colNeighbor].isClosed)) continue;
                            if (field[rowNeighbor][colNeighbor].hasFlag) {
                                flags++;
                                continue;
                            }
                            if (field[rowNeighbor][colNeighbor].isClosed) {
                                lastCell = field[rowNeighbor][colNeighbor];
                                closedCells++;
                            }
                        }
                    }
                    if (cell.mineCounter - flags == 0 && closedCells > 0 && lastCell!=null)
                        return lastCell.row + " " + lastCell.col + " lclick";
                    if (cell.mineCounter - flags == closedCells && closedCells > 0 && lastCell!=null) {
                        return lastCell.row + " " + lastCell.col + " rclick";
                    }
                } else if (field[row][col].isClosed && (!field[row][col].hasFlag) &&
                           nextMove.isEmpty()) nextMove = row + " " + col + " lclick";
            }
        }
        return nextMove;
    }

    public static void rClickAction(Cell[][] field, int row, int col) {
        field[row][col].hasFlag = !field[row][col].hasFlag;
    }

    public static void lClickAction(Cell[][] field, int row, int col) {
        if (field[row][col].hasFlag || (!field[row][col].isClosed)) return;
        if (field[row][col].hasMine) {
            System.out.println("Clicked on mine!!!!!!");
            return;
        }
        if (field[row][col].mineCounter > 0) {
            field[row][col].isClosed = false;
            return;
        }
        if (field[row][col].mineCounter == 0) {
            field[row][col].isClosed = false;
            for (int rowNeighbor = Math.max(0, row - 1);
                 rowNeighbor < Math.min(field.length, row + 2); rowNeighbor++){
                for (int colNeighbor = Math.max(0, col - 1);
                     colNeighbor < Math.min(field[rowNeighbor].length, col + 2); colNeighbor++){
                    lClickAction(field, rowNeighbor, colNeighbor);
                }
            }
        }
    }

    public static boolean winCondition(Cell[][] field) {
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                if (!field[i][j].isClosed) continue;
                if ((!field[i][j].hasMine) || (field[i][j].hasMine && (!field[i][j].hasFlag)))
                    return false;
            }
        }
        return true;
    }
}
