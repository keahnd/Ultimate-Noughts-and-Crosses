import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Naught extends Group {

    public Naught (double x, double y) {
        super();
        getChildren().addAll(new Circle(x, y,20, Color.RED), new Circle(x, y,15, Color.WHITE));
    }

    public Naught (double x, double y, double size) {
        super();
        Rectangle bg = new Rectangle(x-20*size, y-20*size,40*size, 40*size);
        bg.setFill(Color.WHITE);
        getChildren().addAll(bg, new Circle(x, y,20*size, Color.RED), new Circle(x, y,15*size, Color.WHITE));
    }

    public Naught (){}
}