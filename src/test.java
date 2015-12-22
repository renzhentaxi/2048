import Model.Board;
import Model.Location;
import Model.Tile;

public class test
{


    public static void main(String[] args)
    {
        Board board = new Board(3, 3);
        board.MAP.put(new Location(0, 0), new Tile(2));
        board.MAP.put(new Location(0, 1), new Tile(2));
        board.MAP.put(new Location(0, 2), new Tile(4));
        board.print();
        board.move(Location.Up);
        board.print();
    }


}
