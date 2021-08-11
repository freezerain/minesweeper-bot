import java.util.*;

public class MsSolver {
    public static Set<String> nextMoves(Cell[][] field) {
        Cell firstEmptyCell = null;
        int randomCounter = 0;
        Random r = new Random();
        Set<String> moveList = new HashSet<>();
        for (int row = 0; row < field.length; row++)
            for (int col = 0; col < field[row].length; col++){
                if ((!field[row][col].isClosed) && field[row][col].mineCounter > 0) {
                    Cell cell = field[row][col];
                    int flags = 0;
                    ArrayList<Cell> closedNeighbors = new ArrayList<>();
                    for (int rowNeighbor = Math.max(0, row - 1);
                         rowNeighbor < Math.min(field.length, row + 2); rowNeighbor++)
                        for (int colNeighbor = Math.max(0, col - 1);
                             colNeighbor < Math.min(field[rowNeighbor].length, col + 2);
                             colNeighbor++)
                            if (field[rowNeighbor][colNeighbor].isClosed) {
                                if (field[rowNeighbor][colNeighbor].hasFlag) flags++;
                                else closedNeighbors.add(field[rowNeighbor][colNeighbor]);
                            }
                    if (cell.mineCounter - flags == 0 && closedNeighbors.size() > 0)
                        closedNeighbors.forEach(c -> moveList.add(c.row + " " + c.col));
                    if (cell.mineCounter - flags == closedNeighbors.size() &&
                        closedNeighbors.size() > 0) for (Cell c: closedNeighbors)
                        field[c.row][c.col].hasFlag = true;
                } else if ((firstEmptyCell == null || firstEmptyCell.hasFlag ||
                            r.nextInt(10) > randomCounter) && field[row][col].isClosed &&
                           (!field[row][col].hasFlag)) {
                    firstEmptyCell = field[row][col];
                    randomCounter++;
                }
            }
        if (moveList.isEmpty() && firstEmptyCell != null)
            moveList.add(firstEmptyCell.row + " " + firstEmptyCell.col);
        return moveList;
    }

}
