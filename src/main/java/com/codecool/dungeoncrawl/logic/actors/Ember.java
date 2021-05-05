package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Ember extends Actor {
    private final double moveChance = 0.875d;

    public Ember(Cell cell) {
        super(cell, 8);
        this.setDmg(4);
        this.setIsEnemy();
    }

    @Override
    public String getTileName() { return "ember"; }

    @Override
    public void onRefresh() {
        int[][] offsets = { {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        boolean attackDone = false;
        for (int[] offset : offsets) {
            Actor target = getCell().getNeighbour(offset[0], offset[1]).getActor();
            if (target != null && target.getClass() == Player.class) {
                attackDone = true;
                attack(offset[0], offset[1]);
                break;
            }
        }
        if (!attackDone) {
            int[] bounds = getCell().getMapParams();
            int x = (int) Math.floor(Math.random() * bounds[0]);
            int y = (int) Math.floor(Math.random() * bounds[1]);
            if (Math.random() < moveChance)
                move(x - getCell().getX(), y - getCell().getY(), CellType.FLOOR);
        }
    }
}
