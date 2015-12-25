package Model;

import Model.Change.ChangeType;

import java.util.*;
import java.util.stream.Collectors;

public class Board
{
    public final Map<Location, Tile> MAP;
    public final int ROW_COUNT, COL_COUNT, CAPACITY;
    public final List<Location> ALL_LOCS;
    public final Random RAN_GEN;
    public List<Change> changes;
    private int moveCount;

    //Constructors
    public Board(int rowCount, int colCount)
    {
        ROW_COUNT = rowCount;
        COL_COUNT = colCount;
        CAPACITY = ROW_COUNT * COL_COUNT;

        MAP = new HashMap<>(CAPACITY);
        ALL_LOCS = new ArrayList<>(CAPACITY);
        for (int x = 0; x < COL_COUNT; x++)
        {
            for (int y = 0; y < ROW_COUNT; y++)
            {
                ALL_LOCS.add(new Location(x, y));
            }
        }

        RAN_GEN = new Random();
        moveCount = 0;
        changes = new ArrayList<>(CAPACITY);
    }

    public void add(int count)
    {
        List<Location> locs = emptyLocs();
        for (int i = 0; i < count; i++)
        {
            int index = RAN_GEN.nextInt(locs.size());
            int value = RAN_GEN.nextBoolean() ? 2 : 4;
            Location loc = locs.get(index);
            Tile tile = new Tile(value);

            MAP.put(loc, tile);
            locs.remove(index);
            changes.add(new Change(ChangeType.add, tile, loc));
        }
    }

    public void move(Location dir)
    {
        moveCount += 1;
        List<Location> locs = new ArrayList<>(MAP.keySet());
        List<Location> Merged = new ArrayList<>(locs.size() / 2);

        locs.sort(LocationComparators.getComparator(dir));

        for (Location loc : locs)
        {
            Location shiftLoc = loc;
            Location adjacent = loc.translate(dir);
            while (isValidLoc(adjacent) && isEmpty(adjacent))
            {
                shiftLoc = adjacent;
                adjacent = adjacent.translate(dir);
            }
            if (shiftLoc != loc)
            {
                //shifting occurs here
                Tile tile = MAP.get(loc);
                MAP.put(shiftLoc, tile);
                MAP.remove(loc);

                changes.add(new Change(ChangeType.shift, tile, shiftLoc));
            }
            if (isValidLoc(adjacent) && canMerge(shiftLoc, adjacent) && !Merged.contains(adjacent))
            {
                // merge occurs here
                Tile tile = MAP.get(shiftLoc);
                Tile adjTile = MAP.get(adjacent);
                MAP.remove(shiftLoc);
                MAP.put(adjacent, tile.promote());
                Merged.add(adjacent);

                changes.add(new Change(ChangeType.merge, tile, adjacent));
                changes.add(new Change(ChangeType.remove, adjTile, null));
            }
        }

        if (hasRoom(2))
        {
            add(1);
        } else if (hasRoom(1)) // check for game over if there is only 1 more space
        {
            add(1);
            if (!(canMove(Location.Left) || canMove(Location.Right) || canMove(Location.Up) || canMove(Location.Down)))
            {
                System.out.println("game Over");
            }
        }

    }

    public boolean canMove(Location dir)
    {
        return MAP.keySet().stream().anyMatch(location ->
        {
            Location adjacent = location.translate(dir);
            return isValidLoc(adjacent) && (isEmpty(adjacent) || canMerge(location, adjacent));
        });
    }

    //private
    private boolean hasRoom(int count)
    {
        return CAPACITY - MAP.size() >= count;
    }

    private List<Location> emptyLocs()
    {
        return ALL_LOCS.stream().filter(loc -> !MAP.containsKey(loc)).collect(Collectors.toList());
    }

    private boolean isValidLoc(Location loc)
    {
        return loc.X >= 0 && loc.Y >= 0 && loc.X < COL_COUNT && loc.Y < ROW_COUNT;
    }

    private boolean isEmpty(Location loc)
    {
        return !MAP.containsKey(loc);
    }

    private boolean canMerge(Location loc1, Location loc2)
    {
        Tile t1 = MAP.get(loc1);
        Tile t2 = MAP.get(loc2);
        return t1.value == t2.value;
    }
}
