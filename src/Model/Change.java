package Model;

public class Change
{
    public ChangeType type;
    public Location loc;
    public Tile tile;

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
