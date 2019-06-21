import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Board extends GridPane {
    GridPane[][] boards;
    int[][][][] pieceWithin = new int[3][3][3][3];
    int[][] won = new int[3][3];
    boolean[][] isOver;

    public Board() {
        boards = new GridPane[3][3];
        isOver = new boolean[3][3];
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setHgap(1);
        setVgap(1);
        setPadding(new Insets(5, 5, 5, 5));
        final int numCols = 3;
        final int numRows = 3;

        for (int x = 0; x < numCols; x++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            getColumnConstraints().add(colConst);
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            getRowConstraints().add(rowConst);
        }

        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                boards[a][b] = new GridPane();
                boards[a][b].setHgap(1);
                boards[a][b].setVgap(1);
                boards[a][b].setPadding(new Insets(1, 1, 1, 1));

                for (int c = 0; c < 3; c++) {
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100.0 / numCols);
                    boards[a][b].getColumnConstraints().add(colConst);
                    RowConstraints rowConst = new RowConstraints();
                    rowConst.setPercentHeight(100.0 / numRows);
                    boards[a][b].getRowConstraints().add(rowConst);
                }

                Pane[][] pieces = new Pane[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pieces[i][j] = new Pane();
                        pieces[i][j].setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

                        boards[a][b].add(pieces[i][j], i, j);
                    }
                }

                add(boards[a][b], a, b);

            }
        }

    }

    Node getChildAtRowCol(int rowP, int colP, int rowC, int colC) {
        for (Node child : boards[rowP][colP].getChildren()) {
            if (boards[rowP][colP].getColumnIndex(child) == colC && boards[rowP][colP].getRowIndex(child) == rowC) {
                return child;
            }
        }

        return null;
    }

    void set_pieceWithin(int i, int j, int a, int b, boolean isCross) {
        if (isCross)
            pieceWithin[i][j][a][b] = 1;
        else
            pieceWithin[i][j][a][b] = -1;
    }

    int get_pieceWithin(int i, int j, int a, int b) {
        return pieceWithin[i][j][a][b];
    }

    void set_won(int i, int j, boolean isCross) {
        if(isCross)
            won[i][j] = 1;
        else
            won[i][j] = -1;
    }

    boolean get_won(int i, int j) {
        return won[i][j] != 0;
    }

    void set_nextMove(int i, int j) {
        boards[i][j].setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    void reset_nextMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boards[i][j].setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    void playAny() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(!(get_won(i,j)))
                boards[i][j].setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    boolean allowed(int i, int j, int nextMove_bx, int nextMove_by, boolean first) {
        if(first)
            return true;
        if (get_won(nextMove_bx, nextMove_by))
            return !(i == nextMove_bx && j == nextMove_by);

        return (i == nextMove_bx && j == nextMove_by);
    }

    boolean game_over(int piece){
        for(int i = 0; i < 3; i++){
            boolean rowWin = true;
            for(int j = 0; j < 3; j++){
                if(won[i][j] != piece){
                    rowWin = false;
                    break;
                }
            }
            if(rowWin)
                return true;
        }

        for(int j = 0; j < 3; j++){
            boolean colWin = true;
            for(int i = 0; i < 3; i++){
                if(won[i][j] != piece){
                    colWin = false;
                    break;
                }
            }
            if(colWin)
                return true;
        }

        boolean mainDiagWin = true;
        for(int i = 0; i < 3; i++){
            if(won[i][i] != piece){
                mainDiagWin = false;
                break;
            }
        }
        if(mainDiagWin)
            return true;

        boolean diagWin = true;
        for(int i = 0; i < 3; i++) {
            if(won[i][2-i] != piece){
                diagWin = false;
                break;
            }
        }
        return diagWin;
    }
}