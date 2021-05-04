package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

public class Player extends Actor {
    public Item[] inventory = {new Sword(), new Key()};

    public Player(Cell cell) {
        super(cell);
    }

    public String getInventory() {
        StringBuilder sb = new StringBuilder("");
        for (Item item: inventory) {
            sb.append("\n" + item.getTileName());
        }
        return sb.toString();
    }


    public String getTileName() {
        return "player";
    }
}
