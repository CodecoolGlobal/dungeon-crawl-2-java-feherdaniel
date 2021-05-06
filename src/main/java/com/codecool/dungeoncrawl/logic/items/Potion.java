package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Potion extends Item{

    private int health = 5;

    public Potion(Cell cell) {super(cell);}

    public String getTileName() {
        return "potion";
    }

    public int getHealth() {
        return health;
    }
}
