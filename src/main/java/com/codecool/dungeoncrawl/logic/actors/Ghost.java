package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Ghost extends Actor {
    private final double moveChance = 0.3;
    Cell cell;

    public Ghost(Cell cell) {
        super(cell, 13);
        this.setDmg(3);
        this.setIsEnemy();
        this.cell = cell;
    }

    @Override
    public String getTileName() { return "ghost"; }

    @Override
    public void onRefresh() {
        int[][] offsets = { {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        boolean attackDone = false;
        for (int[] offset : offsets) {
            Actor target;
            try { target = getCell().getNeighbour(offset[0], offset[1]).getActor(); }
            catch (ArrayIndexOutOfBoundsException oob) { target = null; }
            if (target != null && target.getClass() == Player.class) {
                attackDone = true;
                attack(offset[0], offset[1]);
                break;
            }
        }
        if (!attackDone) {
            if (Math.random() < moveChance) return;
            int[] targetOffset = offsets[(int)(Math.random() * 4)];
            Cell target;
            try { target = getCell().getNeighbour(targetOffset[0], targetOffset[1]); }
            catch (ArrayIndexOutOfBoundsException oob) { target = null; }
            if (target != null && target.getActor() == null)
                move(targetOffset[0], targetOffset[1],
                        CellType.EMPTY, CellType.FLOOR, CellType.WALL, CellType.DOOR, CellType.OPEN_DOOR);
        }
    }

}
