package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;
import java.util.ArrayList;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private final GameMap gameMap;
    private final int x, y;
    private Item item;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() { return type; }
    public void setType(CellType type) { this.type = type; }

    public Actor getActor() { return actor; }
    public void setActor(Actor actor) { this.actor = actor; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    /**
     * Get Cell object at relative offset on same GameMap as caller
     * @param dx Relative (signed) horizontal offset
     * @param dy Relative (signed) vertical offset
     * @return Cell object at specified offset
     */
    public Cell getNeighbour(int dx, int dy) {
        if (x + dx < gameMap.getWidth() && x + dx >= 0)
            if (y + dy < gameMap.getHeight() && y + dy >= 0)
                return gameMap.getCell(x + dx, y + dy);
        return null;
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    /**
     * Get width and height of the map
     * @return int[] containing gameMap.getWidth() and gameMap.getHeight()
     */
    public int[] getMapParams() { return new int[]{gameMap.getWidth(), gameMap.getHeight()}; }

    public Player getAssociatedPlayer() { return gameMap.getPlayer(); }

}
