public enum Columns {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10);

    final int columnNumber;

    Columns(int columnNumber){
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public static Columns getColumn(int columnNumber){
        for(Columns myColumn: Columns.values()){
            if (myColumn.columnNumber == columnNumber){
                return myColumn;
            }
        }
        return null;
    }
}
