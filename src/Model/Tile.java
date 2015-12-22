package Model;

public class Tile
{
    public final int VALUE;

    public Tile(int value)
    {
        VALUE = value;
    }

    @Override
    public String toString()
    {
        return Integer.toString(VALUE);
    }

    public Tile promote()
    {
        return new Tile(VALUE * 2);
    }
}
