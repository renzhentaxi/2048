package View;

import Model.Board;
import Model.Location;
import Presenter.BoardPresenter;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BoardView extends GridPane
{
    BoardPresenter presenter;
    double cellWidth, cellHeight;

    public BoardView(Board board)
    {
        presenter = new BoardPresenter(board, this);
        getStyleClass().add("Board");
        wireKeyEvents();
    }


    public void layoutBoard(int column, int row)
    {
        cellHeight = 100d / row;
        cellWidth = 100d / column;
        for (int i = 0; i < column; i++)
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.CENTER);
            cc.setPercentWidth(cellWidth);
            getColumnConstraints().add(cc);
        }
        for (int i = 0; i < column; i++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setValignment(VPos.CENTER);
            rc.setPercentHeight(cellHeight);
            getRowConstraints().add(rc);
        }
    }

    public void update()
    {
        presenter.processChanges();
    }

    private void wireKeyEvents()
    {
        setOnKeyReleased(
                me ->
                {
                    Location dir = null;
                    switch (me.getCode())
                    {
                        case LEFT:
                        case A:
                            dir = Location.Left;
                            break;
                        case RIGHT:
                        case D:
                            dir = Location.Right;
                            break;
                        case UP:
                        case W:
                            dir = Location.Up;
                            break;
                        case DOWN:
                        case S:
                            dir = Location.Down;
                            break;
                    }

                    if (dir != null)
                    {
                        presenter.move(dir);
                    }
                }
        );
    }

}