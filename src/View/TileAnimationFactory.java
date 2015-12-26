package View;

import Model.Location;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class TileAnimationFactory
{
    public static Timeline genCreateAnimation(TileView tileView)
    {
        KeyValue fScaleX = new KeyValue(tileView.scaleXProperty(), 1d);
        KeyValue fScaleY = new KeyValue(tileView.scaleYProperty(), 1d);

        KeyFrame frame2 = new KeyFrame(Duration.millis(300d), fScaleX, fScaleY);

        return new Timeline(frame2);
    }

    public static Timeline genPromoteAnimation(TileView tileView)
    {
        KeyValue iScaleX = new KeyValue(tileView.scaleXProperty(), 1d);
        KeyValue iScaleY = new KeyValue(tileView.scaleYProperty(), 1d);
        KeyValue fScaleX = new KeyValue(tileView.scaleXProperty(), 1.2d);
        KeyValue fScaleY = new KeyValue(tileView.scaleYProperty(), 1.2d);

        KeyFrame mFrame = new KeyFrame(Duration.millis(100d), ae -> tileView.update(), fScaleX, fScaleY);
        KeyFrame fFrame = new KeyFrame(Duration.millis(200d), iScaleX, iScaleY);

        return new Timeline(mFrame, fFrame);
    }

    public static Timeline genShiftAnimation(TileView tileView, Location moveTo)
    {
        DoubleProperty target;
        double endValue;
        int displacement;
        int x = GridPane.getColumnIndex(tileView);
        int y = GridPane.getRowIndex(tileView);

        if (x == moveTo.X)
        {
            //vertical
            target = tileView.translateYProperty();
            endValue = moveTo.Y * tileView.getHeight() - tileView.getLayoutY();
            displacement = y > moveTo.Y ? y - moveTo.Y : moveTo.Y - y;

        } else
        {
            target = tileView.translateXProperty();
            endValue = moveTo.X * tileView.getWidth() - tileView.getLayoutX();
            displacement = x > moveTo.X ? x - moveTo.X : moveTo.X - x;
        }

        EventHandler<ActionEvent> onFinished = event -> {
            target.set(0d);
            GridPane.setConstraints(tileView, moveTo.X, moveTo.Y);
        };

        KeyValue fValue = new KeyValue(target, endValue);
        KeyFrame fFrame = new KeyFrame(Duration.millis(displacement * 75d), onFinished, fValue);

        return new Timeline(fFrame);
    }

    public static Timeline genRemoveAnimation(TileView tileView)
    {
        tileView.toBack();
        KeyFrame iFrame = new KeyFrame(Duration.millis(500d));
        return new Timeline(iFrame);
    }
}
