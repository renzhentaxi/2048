import Model.Board;
import Model.Location;
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
        setGridLinesVisible(true);
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
                            dir = Location.Left;
                            break;
                        case RIGHT:
                            dir = Location.Right;
                            break;
                        case UP:
                            dir = Location.Up;
                            break;
                        case DOWN:
                            dir = Location.Down;
                    }

                    if (dir != null)
                    {
                        presenter.move(dir);
                    }
                }
        );
    }
}