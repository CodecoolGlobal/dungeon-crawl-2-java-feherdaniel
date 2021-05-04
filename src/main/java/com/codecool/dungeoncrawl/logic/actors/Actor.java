package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int maxHealth = 10;
    private int health = maxHealth;
    private int dmg = 5;
    private boolean isEnemy = false;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public Actor(Cell cell, int maxHealth) {

    }

    public void move(int dx, int dy) {
        if (cell.getNeighbor(dx, dy).getType() != CellType.WALL &&
                cell.getNeighbor(dx, dy).getActor() == null) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public int getHealth() {
        return health;
    }

    public int getDmg() { return dmg; }

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
        health -= dmg;
        if (health <= 0) die();
    }

    public void heal(int hp) { health = Math.min(maxHealth, health + hp); }

    public void die() {
        cell.setActor(null);
    }
}
