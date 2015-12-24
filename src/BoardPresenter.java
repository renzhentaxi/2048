import Model.Board;
import Model.Location;


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

    public void processChanges()
    {

    }


}