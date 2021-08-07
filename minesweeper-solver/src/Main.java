public class Main {
    public static void main(String[] args) {
        Cell[][] field = MinesweeperGenerator.generate(80, 80, 150);
        if(field==null) {
            System.out.println("wrong field error!");
            return;
        }
        System.out.println("Starting field:");
        MinesweeperGenerator.printField(field);
        System.out.println("---------------------------------");
        boolean isSolved = MsSolver.winCondition(field);
        int moves = 0;
        long initTime = System.currentTimeMillis();
        while ((!isSolved) && moves < 1000) {
            long iterTime = System.currentTimeMillis();
            String nextMove = MsSolver.nextMove(field);
            String[] nextMoveArray = nextMove.split(" ");
            System.out.println("Move " + moves + ": " + nextMove);
            if (nextMoveArray[2].equals("lclick")) {
                MsSolver.lClickAction(field, Integer.parseInt(nextMoveArray[0]),Integer.parseInt(nextMoveArray[1]));
            } else {
                MsSolver.rClickAction(field, Integer.parseInt(nextMoveArray[0]),Integer.parseInt(nextMoveArray[1]));
            }
            isSolved = MsSolver.winCondition(field);
            System.out.println("isSolved: " + isSolved + ". Time: " + (System.currentTimeMillis()-iterTime)+"ms.");
            MinesweeperGenerator.printField(field);
            moves++;
            System.out.println("---------------------------------");
        }
        System.out.println((isSolved? "Solved" : "Not Solved") + " in " + (System.currentTimeMillis() - initTime) + "ms. with " + moves + " moves.");
    }
}
