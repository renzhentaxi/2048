package Model;

public class Change
{
    public final ChangeType type;
    public final Tile tile;
    public Location loc;

    public Change(ChangeType type, Tile tile, Location loc)
    {
        this.type = type;
        this.tile = tile;
        this.loc = loc;
    }

    public enum ChangeType
    {
        add, shift, promote, remove
    }

}
