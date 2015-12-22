package Model;

import java.util.Comparator;


public class LocationComparators
{
    public static final Comparator<Location> Left = (loc1, loc2) -> loc1.X - loc2.X;
    public static final Comparator<Location> Right = (loc1, loc2) -> loc2.X - loc1.X;
    public static final Comparator<Location> Up = (loc1, loc2) -> loc1.Y - loc2.Y;
    public static final Comparator<Location> Down = (loc1, loc2) -> loc2.Y - loc1.Y;

    public static Comparator<Location> getComparator(Location dir)
    {
        if (dir.X == 0)
        {
            return dir.Y > 0 ? Down : Up;
        }
        return dir.X > 0 ? Right : Left;
    }
}
