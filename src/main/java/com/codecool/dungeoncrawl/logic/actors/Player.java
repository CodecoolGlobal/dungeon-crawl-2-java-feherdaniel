package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.MapLoader;

import java.util.ArrayList;

public class Player extends Actor {
    public ArrayList<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
    }
    public Player(Cell cell, int maxHealth) {
        super(cell, maxHealth);
    }



    public String getInventory() {
        StringBuilder sb = new StringBuilder("");
        for (Item item: inventory) {
            if (item != inventory.get(0)) {
                sb.append(item.getTileName() + "\n");
            }
        }
        return sb.toString();
    }

    public String getFirstItem() {
        for (Item item: inventory) {
            if (inventory.get(0) != null) {
                return inventory.get(0).getTileName();
            }
            break;
        }
        return " ";
    }

    public String getTileName() {
        return "player";
    }

    public void addToInventory(Item item) {
        if (item instanceof Sword) {
            if (((Sword)item).getDamage() > getDmg())
                setDmg( ((Sword) item).getDamage() );
        }
        if (item instanceof Potion) {
            heal(((Potion)item).getHealth());
        }
        else inventory.add(item);
    }

    @Override
    public void die() {
        cell.setActor(null);
        MapLoader.onPlayerDeath();
    }

    @Override
    public void move(int dx, int dy) {
        if (cell.getNeighbour(dx, dy).getType() != CellType.WALL && cell.getNeighbour(dx, dy).getType() != CellType.DOOR &&
                cell.getNeighbour(dx, dy).getActor() == null) {
            Cell nextCell = cell.getNeighbour(dx, dy);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        else if (cell.getNeighbour(dx, dy).getType() == CellType.DOOR) {
            for (Item item: inventory) {
                if (item instanceof Key) {
                    Cell nextCell = cell.getNeighbour(dx, dy);
                    cell.setActor(null);
                    nextCell.setActor(this);
                    cell = nextCell;
                    inventory.remove(item);
                    cell.setType(CellType.OPEN_DOOR);
                    break;
                }
            }
        }
    }
}
