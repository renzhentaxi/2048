package View;

import Model.Tile;
import javafx.scene.control.Label;

public class TileView extends Label
{
    public final Tile model;
    public boolean removeOnNext = false;

    public TileView(Tile tile)
    {
        model = tile;
        update();
        setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getStyleClass().add("Tile");
        setScaleX(0d);
        setScaleY(0d);

    }

    public void update()
    {
        setText(model.toString());
    }

}
