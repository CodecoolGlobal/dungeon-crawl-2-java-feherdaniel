package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Ember extends Actor {

    public Ember(Cell cell) {
        super(cell, 8);
        this.setDmg(4);
        this.setIsEnemy();
    }

    @Override
    public String getTileName() { return "ember"; }

    @Override
    public void onRefresh() {
        int[] bounds = getCell().getMapParams();
        int x = (int)Math.floor(Math.random() * bounds[0]);
        System.out.println(x + " " + bounds[0]);
        int y = (int)Math.floor(Math.random() * bounds[1]);
        System.out.println(y + " " + bounds[1]);
        move(x - getCell().getX(), y - getCell().getY());
    }
}
