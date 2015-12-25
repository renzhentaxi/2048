package View;

import Model.Change;
import Model.Tile;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class TileView extends Label
{
    public final Tile model;
    private boolean destroyOnFinish = false;
    private List<Timeline> animationQueue;


    public TileView(Tile tile)
    {
        model = tile;
        update();
        setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getStyleClass().add("Tile");

        animationQueue = new ArrayList<>();
    }

    public void update()
    {
        setText(model.toString());
    }

    public synchronized void playAnim()
    {
        if (animationQueue.size() > 0)
        {
            for (int i = 0; i < animationQueue.size() - 1; i++)
            {
                Timeline next = animationQueue.get(i + 1);
                animationQueue.get(i).setOnFinished(ae -> next.play());
            }
            animationQueue.get(animationQueue.size() - 1).setOnFinished(ae -> {
                if (destroyOnFinish)
                {
                    ((Pane) getParent()).getChildren().remove(this);
                }
                animationQueue.clear();
            });
            animationQueue.get(0).play();
        }
    }

    public synchronized void addAnimation(Change change)
    {
        switch (change.type)
        {
            case add:
                animationQueue.add(TileAnimationFactory.genCreateAnimation(this));
                break;
            case shift:
                animationQueue.add(TileAnimationFactory.genShiftAnimation(this, change.loc));
                break;
            case promote:
                animationQueue.add(TileAnimationFactory.genPromoteAnimation(this));
                break;
            case remove:
                destroyOnFinish = true;
                animationQueue.add(TileAnimationFactory.genRemoveAnimation(this));
                break;
        }
    }
}
