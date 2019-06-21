import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.application.Application;

public class UltimateXOs extends Application {

    private final int SCREENWIDTH = 500, SCREENHEIGHT = 500;
    private boolean isCross;
    private int nextMove_bx, nextMove_by;
    private boolean first = true;

    @Override
    public void start(Stage primaryStage){
        initUI(primaryStage);
    }

    public void initUI(Stage primaryStage){
        Board board = new Board();
        StackPane root = new StackPane();
        root.getChildren().add(board);
        Text game_over = new Text ("");
        root.getChildren().add(game_over);
        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);
        primaryStage.setTitle("UXO");
        primaryStage.setScene(scene);
        primaryStage.show();

        board.setOnMouseClicked(e -> {
            int width;
            int height;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int a = 0; a < 3; a++) {
                        for (int b = 0; b < 3; b++) {
                            width = i*(SCREENWIDTH/3);
                            height = j*(SCREENHEIGHT/3);
                            Pane child = (Pane) board.getChildAtRowCol(i, j, a, b);

                            if(board.game_over(1)){
                                game_over.setText("CROSS WINS!!");
                                game_over.setFont(Font.font("Verdana", 50));
                                game_over.setFill(Color.DARKBLUE);
                                board.reset_nextMove();
                                game_over.toFront();
                            }

                            else if(board.game_over(-1)){
                                game_over.setText("NOUGHTS WINS!!");
                                game_over.setFont(Font.font("Verdana", 50));
                                game_over.setFill(Color.DARKBLUE);
                                board.reset_nextMove();
                                game_over.toFront();
                            }

                            double leftBounds = child.getBoundsInParent().getMinX() + width;
                            double topBounds = child.getBoundsInParent().getMinY() + height;
                            double rightBounds = child.getBoundsInParent().getMaxX() + width;
                            double bottomBounds = child.getBoundsInParent().getMaxY() + height;

                            boolean left = e.getX() > leftBounds;
                            boolean up = e.getY() > topBounds;
                            boolean right = e.getX() < rightBounds;
                            boolean down = e.getY() < bottomBounds;


                            if (left && up && right && down && !(board.get_won(i,j)) && board.allowed(i,j, nextMove_bx, nextMove_by, first) ){
                                first = false;
                                double centerX = (rightBounds - leftBounds) / 2;
                                double centerY = (bottomBounds - topBounds) / 2;

                                if (child.getChildren().isEmpty()) {
                                    board.reset_nextMove();
                                    nextMove_bx = b;
                                    nextMove_by = a;

                                    if (isCross) {
                                        child.getChildren().add(new Cross(centerX, centerY));
                                        board.set_pieceWithin(i, j, a, b, isCross);
                                        if(checkWin(i,j, isCross, board, centerX, centerY)) {
                                            board.set_won(i,j,isCross);
                                            board.add(new Cross(0, 0, 4.025),i,j);
                                        }
                                        isCross = false;
                                    }

                                    else if (!isCross) {
                                        child.getChildren().add(new Naught(centerX, centerY));
                                        board.set_pieceWithin(i, j, a, b, isCross);
                                        if(checkWin(i,j, isCross, board, centerX, centerY)) {
                                            board.set_won(i,j, isCross);
                                            board.add(new Naught(0, 0, 4.025),i,j);
                                        }
                                        isCross = true;
                                    }

                                    if(board.get_won(nextMove_bx,nextMove_by))
                                        board.playAny();
                                    else
                                        board.set_nextMove(nextMove_bx, nextMove_by);
                                }


                                else {
                                    System.out.println("Choose a different box");
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    boolean checkWin(int bx, int by, boolean isCross, Board board, double centerX, double centerY){
        int check_piece;
        if(isCross)
            check_piece = 1;
        else
            check_piece = -1;

        for(int i = 0; i < 3; i++){
            boolean rowWin = true;
            for(int j = 0; j < 3; j++){
                if(board.get_pieceWithin(bx, by, i, j) == 0 || board.get_pieceWithin(bx, by, i, j) != check_piece){
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
                if(board.get_pieceWithin(bx, by, i, j) == 0 || board.get_pieceWithin(bx, by, i, j) != check_piece){
                    colWin = false;
                    break;
                }
            }
            if(colWin)
                return true;
        }

        boolean mainDiagWin = true;
        for(int i = 0; i < 3; i++){
            if(board.get_pieceWithin(bx, by, i, i) == 0 || board.get_pieceWithin(bx, by, i, i) != check_piece){
                mainDiagWin = false;
                break;
            }
        }
        if(mainDiagWin)
            return true;

        boolean diagWin = true;
        for(int i = 0; i < 3; i++) {
            if(board.get_pieceWithin(bx, by, i, 2-i) == 0 || board.get_pieceWithin(bx, by, i, 2-i) != check_piece){
                diagWin = false;
                break;
            }
        }
        if(diagWin)
            return true;

        return false;
    }

    public static void main (String []args) {
        launch(args);
    }
}