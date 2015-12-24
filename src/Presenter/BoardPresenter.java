package Presenter;

import Model.Board;
import Model.Change;
import Model.Location;
import Model.Tile;
import View.BoardView;
import View.TileView;


public class BoardPresenter
{
    Board model;
    BoardView view;

    public BoardPresenter(Board board, BoardView boardView)
    {
        model = board;
        view = boardView;
        view.layoutBoard(model.COL_COUNT, model.ROW_COUNT);
    }

    public void move(Location dir)
    {
        if (model.canMove(dir))
        {
            model.move(dir);

            processChanges();
        }

    }

    private TileView getTileView(Tile tile)
    {
        return view.getChildren().stream().filter(node -> node instanceof TileView).map(node -> (TileView) node).filter(tileView -> tileView.model == tile).findFirst().get();
    }

    public void processChanges()
    {
        for (Change change : model.changes)
        {
            switch (change.type)
            {
                case add:
                {
                    TileView tv = new TileView(change.tile);
                    view.add(tv, change.loc.X, change.loc.Y);
                    tv.animCreated();
                    break;
                }
                case shift:
                {
                    TileView tv = getTileView(change.tile);
                    tv.animMove(change.loc);
                    break;
                }
                case promote:
                {
                    view.add(new TileView(change.tile), change.loc.X, change.loc.Y);
                    break;
                }
                case remove:
                {
                    TileView tv = getTileView(change.tile);
                    view.getChildren().remove(tv);
                }

            }
        }
        model.changes.clear();
    }


}