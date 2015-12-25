package Model;


public class Tile
{
    public int value;

    public Tile(int value)
    {
        this.value = value;
    }

    public Tile promote()
    {
        value *= 2;
        return this;
    }

    @Override
    public String toString()
    {
        return Integer.toString(value);
    }
}
