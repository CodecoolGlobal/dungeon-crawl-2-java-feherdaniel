package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.ArrayList;

public class Player extends Actor {
    public ArrayList<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
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
        inventory.add(item);
        if (item instanceof Sword) {
            if (((Sword)item).getDamage() > getDmg())
                setDmg( ((Sword) item).getDamage() );
        }
    }
}
