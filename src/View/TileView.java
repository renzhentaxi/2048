package View;

import Model.Location;
import Model.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TileView extends Label
{
    public Tile model;
    public Timeline timeline;
    private Location moveTo;
    private boolean add = false;
    private boolean move = false;
    private boolean merge = false;
    private boolean destroy = false;
    public TileView(Tile tile)
    {
        model = tile;
        update();
        setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getStyleClass().add("Tile");

        timeline = new Timeline();
    }

    public void update()
    {
        setText(model.toString());
    }

    public void animMove(Location to)
    {
        move = true;
        moveTo = to;
    }

    public void animCreate()
    {
        add = true;
        setScaleX(0d);
        setScaleX(0d);
    }


    public void animMerge()
    {
        merge = true;
    }


    public void animDestroy()
    {
        destroy = true;
        toBack();
    }

    public void playAnim()
    {
        if (add)
        {
            add = false;
            KeyValue iScaleX = new KeyValue(scaleXProperty(), 0d);
            KeyValue iScaleY = new KeyValue(scaleYProperty(), 0d);

            KeyValue fScaleX = new KeyValue(scaleXProperty(), 1d);
            KeyValue fScaleY = new KeyValue(scaleYProperty(), 1d);
            KeyFrame iFrame = new KeyFrame(Duration.millis(150d), iScaleX, iScaleY);
            KeyFrame fFrame = new KeyFrame(Duration.millis(300d), fScaleX, fScaleY);

            timeline.getKeyFrames().addAll(iFrame, fFrame);
        } else if (destroy)
        {
            KeyFrame frame = new KeyFrame(Duration.millis(310d));
            timeline.getKeyFrames().add(frame);
        } else if (move)
        {
            move = false;
            DoubleProperty target;
            double endValue;
            if (GridPane.getColumnIndex(this) == moveTo.X)
            {
                //vertical
                target = translateYProperty();
                endValue = moveTo.Y * getHeight() - getLayoutY();

            } else
            {
                target = translateXProperty();
                endValue = moveTo.X * getWidth() - getLayoutX();
            }
            KeyValue fValue = new KeyValue(target, endValue);
            KeyFrame mFrame = new KeyFrame(Duration.millis(300d), event -> {
                target.set(0d);
                GridPane.setConstraints(this, moveTo.X, moveTo.Y);
            }, fValue);
            timeline.getKeyFrames().add(mFrame);


        }

        timeline.play();
        timeline.setOnFinished(ae -> {
            if (destroy)
            {
                ((Pane) getParent()).getChildren().remove(this);
            }
            if (merge)
            {
                update();
                merge = false;
                KeyValue iScaleX = new KeyValue(scaleXProperty(), 1d);
                KeyValue iScaleY = new KeyValue(scaleYProperty(), 1d);
                KeyValue fScaleX = new KeyValue(scaleXProperty(), 1.2d);
                KeyValue fScaleY = new KeyValue(scaleYProperty(), 1.2d);

                KeyFrame f2Frame = new KeyFrame(Duration.millis(100d), fScaleX, fScaleY);
                KeyFrame f3Frame = new KeyFrame(Duration.millis(200d), iScaleX, iScaleY);

                Timeline mergeTimeLine = new Timeline(f2Frame, f3Frame);
                mergeTimeLine.play();

            }
            timeline.getKeyFrames().clear();

        });
    }
}
