package Presenter;

import Model.Board;
import Model.Change;
import Model.Location;
import Model.Tile;
import View.BoardView;
import View.TileView;

import java.util.Optional;


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

    private Optional<TileView> getTileView(Tile tile)
    {
        return view.getChildren().stream().filter(node -> node instanceof TileView).map(node -> (TileView) node).filter(tileView -> tileView.model == tile).findFirst();
    }

    public void processChanges()
    {
        for (Change change : model.changes)
        {
            Optional<TileView> otv = getTileView(change.tile);
            TileView tv;
            if (otv.isPresent())
            {
                tv = otv.get();
            } else
            {
                tv = new TileView(change.tile);
            }

            switch (change.type)
            {
                case add:
                {
                    view.add(tv, change.loc.X, change.loc.Y);
                    tv.animCreate();
                    break;
                }
                case shift:
                {
                    tv.animMove(change.loc);
                    break;
                }
                case merge:
                {
                    tv.animMove(change.loc);
                    tv.animMerge();
                    break;
                }
                case remove:
                    tv.animDestroy();
                    break;
            }
        }
        model.changes.clear();

        view.getChildren().stream().filter(node -> node instanceof TileView).map(node -> (TileView) node).forEach(tv -> tv.playAnim());
    }


}