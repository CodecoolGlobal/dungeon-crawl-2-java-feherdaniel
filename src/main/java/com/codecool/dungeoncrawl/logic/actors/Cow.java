package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Cow extends Actor {

    public Cow(Cell cell) {
        super(cell, 30898);
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
            Cell targetCell = cell.getNeighbour(offset[0], offset[1]);
            if (targetCell == null) break;
            Actor target = targetCell.getActor();
            if (target != null && target.getClass() == Player.class && getHealth() < getMaxHealth()) {
                attack(offset[0], offset[1]);
                break;
            }
        }
    }
}
