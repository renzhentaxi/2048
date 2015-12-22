package Model;


public class Location
{
    public static final Location Left = new Location(-1, 0);
    public static final Location Right = new Location(1, 0);
    public static final Location Up = new Location(0, -1);
    public static final Location Down = new Location(0, 1);
    public final int X, Y;

    public Location(int x, int y)
    {
        X = x;
        Y = y;
    }

    @Override
    public String toString()
    {
        return "Loc: x " + X + " y " + Y;
    }

    @Override
    public int hashCode()
    {
        return (X * 33) + Y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Location)
        {
            Location loc = (Location) o;
            return loc.X == X && loc.Y == Y;
        }
        return false;
    }

    public Location translate(Location dir)
    {
        return new Location(X + dir.X, Y + dir.Y);
    }
}
