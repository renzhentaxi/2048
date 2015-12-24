package View;

import Model.Tile;
import javafx.scene.control.Label;

public class TileView extends Label
{
    public Tile model;

    public TileView(Tile tile)
    {
        model = tile;
        setText(tile.toString());
    }

}
