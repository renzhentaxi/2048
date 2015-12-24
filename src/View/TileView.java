package View;

import Model.Location;
import Model.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class TileView extends Label
{
    public Tile model;

    public TileView(Tile tile)
    {
        model = tile;
        setText(tile.toString());
        setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getStyleClass().add("Tile");
    }

    public void animMove(Location to)
    {
        double endValue;
        DoubleProperty target;

        if (GridPane.getColumnIndex(this) == to.X)
        {
            // if vertical movement
            endValue = to.Y * getHeight() - getLayoutY();
            target = translateYProperty();
        } else
        {

            endValue = to.X * getWidth() - getLayoutX();
            target = translateXProperty();
        }

        EventHandler<ActionEvent> onFinished = ae ->
        {
            target.set(0d);
            GridPane.setConstraints(this, to.X, to.Y);
        };
        System.out.println(" end " + endValue);

        KeyValue fValue = new KeyValue(target, endValue);
        KeyFrame fFrame = new KeyFrame(Duration.millis(600), onFinished, fValue);
        Timeline timeline = new Timeline(fFrame);
        timeline.play();
    }

    public void animCreated()
    {
        KeyValue iScaleX = new KeyValue(scaleXProperty(), 0d);
        KeyValue iScaleY = new KeyValue(scaleYProperty(), 0d);
        KeyFrame iFrame = new KeyFrame(Duration.seconds(0), iScaleX, iScaleY);
        KeyValue fScaleX = new KeyValue(scaleXProperty(), 1d);
        KeyValue fScaleY = new KeyValue(scaleYProperty(), 1d);
        KeyFrame fFrame = new KeyFrame(Duration.millis(300), fScaleX, fScaleY);
        Timeline timeline = new Timeline(iFrame, fFrame);
        timeline.play();
    }
}
