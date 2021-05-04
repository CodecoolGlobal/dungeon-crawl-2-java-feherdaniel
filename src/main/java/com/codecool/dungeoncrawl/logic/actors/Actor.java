package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.Arrays;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int maxHealth = 10;
    private int health = maxHealth;
    private int dmg = 5;
    private boolean isEnemy = false;
    private int armour = 0;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public Actor(Cell cell, int maxHealth) {
        this.cell = cell;
        this.cell.setActor(this);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void setDmg(int dmg) { this.dmg = dmg; }

    public int getDmg() { return dmg; }

    protected void setIsEnemy() { isEnemy = true; }

    public void onRefresh() {}

    public void move(int dx, int dy) {
        if (cell.getNeighbour(dx, dy).getType() != CellType.WALL &&
                cell.getNeighbour(dx, dy).getActor() == null) {
            Cell nextCell = cell.getNeighbour(dx, dy);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void move(int dx, int dy, CellType... walkableCells) {
        if (Arrays.asList(walkableCells).contains(cell.getNeighbour(dx, dy).getType())) {
            if (cell.getNeighbour(dx, dy).getActor() == null) {
                Cell nextCell = cell.getNeighbour(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() { return maxHealth; }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void damage(int dmg) {
        health -= Math.max(0, dmg - armour);
        if (health <= 0) die();
    }

    public void heal(int hp) { health = Math.min(maxHealth, health + hp); }

    public void die() {
        cell.setActor(null);
    }

    public boolean isEnemy() { return isEnemy; }

    public void attack(int x, int y) {
        Actor target = cell.getNeighbour(x, y).getActor();
        if (target != null && target.isEnemy() != this.isEnemy()) target.damage(dmg);
    }
}
