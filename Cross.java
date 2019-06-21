import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cross extends Group {

    public Cross (double x, double y) {
        super();
        Rectangle vertRect = new Rectangle(x-5, y-20, 10, 40);
        Rectangle horiRect = new Rectangle(x-20, y-5, 40, 10);
        vertRect.setFill(Color.RED);
        horiRect.setFill(Color.RED);
        vertRect.setRotate(45);
        horiRect.setRotate(45);

        getChildren().addAll(horiRect, vertRect);
    }

    public Cross(){}

    public Cross (double x, double y, double size) {
        super();
        Rectangle vertRect = new Rectangle(x-size*5, y-size*20, size*10, size*40);
        Rectangle horiRect = new Rectangle(x-size*20, y-size*5, size*40, size*10);
        vertRect.setFill(Color.RED);
        horiRect.setFill(Color.RED);
        vertRect.setRotate(45);
        horiRect.setRotate(45);
        Rectangle bg = new Rectangle(x-20*size, y-20*size,40*size, 40*size);
        bg.setFill(Color.WHITE);
        getChildren().addAll(bg,horiRect, vertRect);
    }
}