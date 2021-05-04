package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Cow extends Actor {
    private int maxHealth;
    private int health;

    public Cow(Cell cell) {
        super(cell, 30898);
        this.health = maxHealth;
        // takes a byte out of you
        this.setDmg(256);
        this.setIsEnemy();
    }

    @Override
    public String getTileName() { return "cow"; }

    @Override
    public void onRefresh() {
        int[][] offsets = { {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] offset : offsets) {
            Actor target = getCell().getNeighbour(offset[0], offset[1]).getActor();
            if (target != null && target.getClass() == Player.class && health < maxHealth) {
                attack(offset[0], offset[1]);
                break;
            }
        }
    }
}
