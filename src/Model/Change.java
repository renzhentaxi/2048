package Model;

public class Change
{
    public ChangeType type;
    Location location;
    Tile tile;

    public Change(ChangeType type, Tile tile, Location location)
    {
        this.type = type;
        this.tile = tile;
        this.location = location;
    }

    public enum ChangeType
    {
        add, remove, shift, promote,
    }

}
