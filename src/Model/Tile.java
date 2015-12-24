package Model;


public class Tile
{
    public final int VALUE;

    public Tile(int value)
    {
        VALUE = value;
    }

    public Tile promote()
    {
        return new Tile(VALUE * 2);
    }

    @Override
    public String toString()
    {
        return Integer.toString(VALUE);
    }
}
