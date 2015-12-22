import Model.Board;
import Model.Location;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class BoardView extends GridPane
{
    private Board board;

    public BoardView(Board board)
    {
        this.board = board;
        double colPercent = 100d / board.COL_COUNT;
        double rowPercent = 100d / board.ROW_COUNT;
        for (int i = 0; i < board.COL_COUNT; i++)
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHalignment(HPos.CENTER);
            cc.setPercentWidth(colPercent);
            getColumnConstraints().add(cc);
        }
        for (int i = 0; i < board.ROW_COUNT; i++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setValignment(VPos.CENTER);
            rc.setPercentHeight(rowPercent);
            getRowConstraints().add(rc);
        }
        setOnKeyPressed(this::keyHandler);
    }

    public void keyHandler(KeyEvent event)
    {
        switch (event.getCode())
        {
            case LEFT:
            case A:
                board.move(Location.Left);
                break;
            case RIGHT:
            case D:
                board.move(Location.Right);
                break;
            case UP:
            case W:
                board.move(Location.Up);
                break;
            case DOWN:
            case S:
                board.move(Location.Down);
                break;
        }
        update();
    }

    public void update()
    {
        getChildren().clear();
        board.MAP.entrySet().stream().forEach(entry ->
        {
            Label label = new Label(entry.getValue().toString());
            label.setStyle("-fx-font-size:50px;");
            Location loc = entry.getKey();
            add(label, loc.X, loc.Y);
        });
    }
}
