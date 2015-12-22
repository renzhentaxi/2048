package Model;

import java.util.*;
import java.util.stream.Collectors;

public class Board
{
    public final Map<Location, Tile> MAP;
    public final int ROW_COUNT, COL_COUNT, CAPACITY;
    public final List<Location> ALL_LOCS;
    public final Random RAN_GEN;

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

        RAN_GEN = new Random(0);

    }

    public boolean hasRoom(int count)
    {
        return CAPACITY - MAP.size() >= count;
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
        return t1.VALUE == t2.VALUE;
    }

    public boolean canMove(Location dir)
    {
        return MAP.keySet().stream().anyMatch(location ->
        {
            Location adjacent = location.translate(dir);
            return isValidLoc(adjacent) && (isEmpty(adjacent) || canMerge(location, adjacent));
        });
    }

    public List<Location> emptyLocs()
    {
        return ALL_LOCS.stream().filter(loc -> !MAP.containsKey(loc)).collect(Collectors.toList());
    }

    public void add(int count)
    {
        List<Location> locs = emptyLocs();
        for (int i = 0; i < count; i++)
        {
            int index = RAN_GEN.nextInt(locs.size());
            int value = RAN_GEN.nextBoolean() ? 2 : 4;
            MAP.put(locs.get(index), new Tile(value));
            locs.remove(index);
        }
    }

    public void move(Location dir)
    {
        List<Location> locs = new ArrayList<>(MAP.keySet());
        locs.sort(LocationComparators.getComparator(dir));

        List<Location> Merged = new ArrayList<>(locs.size() / 2);
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
                MAP.put(shiftLoc, MAP.get(loc));
                MAP.remove(loc);
            }
            if (isValidLoc(adjacent) && canMerge(shiftLoc, adjacent) && !Merged.contains(adjacent))
            {
                MAP.remove(shiftLoc);
                MAP.put(adjacent, MAP.get(adjacent).promote());
                Merged.add(adjacent);
            }
        }

        if (hasRoom(1))
        {
            add(1);
        } else if (!(canMove(Location.Left) || canMove(Location.Right) || canMove(Location.Up) || canMove(Location.Down)))
        {
            System.out.println("game Over");
        }


    }

    //Debug
    public void print()
    {
        StringBuilder sb = new StringBuilder(CAPACITY + ROW_COUNT);
        for (int y = 0; y < ROW_COUNT; y++)
        {
            for (int x = 0; x < COL_COUNT; x++)
            {
                Tile t = MAP.get(new Location(x, y));
                if (t != null)
                {
                    sb.append(t.toString());
                } else
                    sb.append("0");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
