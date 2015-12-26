package Presenter;

import Model.Board;
import Model.Change;
import Model.Location;
import Model.Tile;
import View.BoardView;
import View.TileAnimationFactory;
import View.TileView;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BoardPresenter
{
    private final Board model;
    private final BoardView view;

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
        List<Timeline> addTimeLines = new ArrayList<>();
        List<Timeline> shiftTimeLines = new ArrayList<>();
        List<TileView> removeList = new ArrayList<>();

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

            if (change.type == Change.ChangeType.add)
            {
                view.add(tv, change.loc.X, change.loc.Y);
            }
            switch (change.type)
            {
                case add:
                    addTimeLines.add(TileAnimationFactory.genCreateAnimation(tv));
                    break;
                case shift:
                    shiftTimeLines.add(TileAnimationFactory.genShiftAnimation(tv, change.loc));
                    break;
                case promote:
                    shiftTimeLines.get(shiftTimeLines.size() - 1).setOnFinished(ae -> TileAnimationFactory.genPromoteAnimation(tv).play());
                    break;
                case remove:
                    tv.toBack();
                    removeList.add(tv);
                    break;
            }
        }
        model.changes.clear();
        Optional<Timeline> longestTimelineOptional = shiftTimeLines.stream().max((t1, t2) -> t1.getCycleDuration().compareTo(t2.getCycleDuration()));
        Duration waitTime = Duration.ZERO;
        if (longestTimelineOptional.isPresent())
        {
            Timeline longestTimeline = longestTimelineOptional.get();
            waitTime = longestTimeline.getCycleDuration();
        }
        shiftTimeLines.forEach(Timeline::play);
        for (Timeline addTl : addTimeLines)
        {
            addTl.setDelay(waitTime);
            addTl.play();

        }
        addTimeLines.get(0).setOnFinished(ae -> removeList.forEach(view.getChildren()::remove));
    }


}