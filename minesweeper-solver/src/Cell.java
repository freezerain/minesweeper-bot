public class Cell {
    int row;
    int col;
    boolean isClosed;
    boolean hasMine;
    boolean hasFlag;
    int mineCounter;

    public Cell(int row, int col, boolean isClosed, boolean hasMine, boolean hasFlag,
                int mineCounter) {
        this.row = row;
        this.col = col;
        this.isClosed = isClosed;
        this.hasMine = hasMine;
        this.hasFlag = hasFlag;
        this.mineCounter = mineCounter;
    }

    public void setParams(int row, int col, String webClass){
        this.row = row;
        this.col = col;
        this.isClosed = webClass.contains("closed");
        this.hasMine = false;
        this.hasFlag = webClass.contains("flag");
        if(webClass.contains("hd_type")) mineCounter = Character.getNumericValue(webClass.charAt(webClass.indexOf("hd_type")+7));
        else mineCounter = 0;
    }

    @Override
    public String toString() {
        if(isClosed && hasFlag) return "!";
        if(isClosed) return "#";
        if(hasMine) return "*";
        if(mineCounter > 0) return String.valueOf(mineCounter);
        if(mineCounter == 0) return "_";
        else return "toString error in Cell.class";
    }
}
