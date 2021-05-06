package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.Arrays;

public abstract class Actor implements Drawable {
    // Engine properties
    protected Cell cell;

    // Gameplay properties
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
    public void setArmour(int armour) { this.armour = armour; }
    public int getArmour() { return armour; }

    /**
     * Signal to the game engine that this Actor should be able to participate in combat
     * with the player (or allies i/a)
     */
    protected void setIsEnemy() { isEnemy = true; }

    /**
     * Function that enables Actors to react to passage of time
     * or movement of player
     */
    public void onRefresh() {}

    /**
     * Move Actor by some horizontal and vertical offset, optionally defining all cells that
     * this Actor can enter
     * Overloads: move(int dx, int dy), move(int dx, int dy, CellType... walkableCells)
     * @param dx Relative (signed) horizontal offset
     * @param dy Relative (signed) vertical offset
     */
    public void move(int dx, int dy) {
        Cell targetCell = cell.getNeighbour(dx, dy);
        if (targetCell == null) return;
        if (targetCell.getType() != CellType.WALL && targetCell.getType() != CellType.DOOR &&
                targetCell.getActor() == null) {
            cell.setActor(null);
            targetCell.setActor(this);
            cell = targetCell;
        }
    }

    /**
     * Move Actor by some horizontal and vertical offset, optionally defining all cells that
     * this Actor can enter
     * Overloads: move(int dx, int dy), move(int dx, int dy, CellType... walkableCells)
     * @param dx Relative (signed) horizontal offset
     * @param dy Relative (signed) vertical offset
     */
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

    public void setCell(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    /**
     * Make this Actor take damage and lose health, and potentially die
     * @param dmg Amount to decrease health by if no armour
     */
    public void damage(int dmg) {
        health -= Math.max(0, dmg - armour);
        if (health <= 0) die();
    }

    /**
     * Make this Actor heal and recover health
     * @param hp Amount of health to recover (if possible)
     */
    public void heal(int hp) { health = Math.min(maxHealth, health + hp); }

    /**
     * Action to take when this Actor loses all health and dies
     */
    public void die() {
        cell.setActor(null);
    }

    public boolean isEnemy() { return isEnemy; }

    /**
     * Attempt to damage an enemy
     * @param dx Relative (signed) horizontal offset
     * @param dy Relative (signed) vertical offset
     */
    public void attack(int dx, int dy) {
        Actor target = cell.getNeighbour(dx, dy).getActor();
        if (target != null && target.isEnemy() != this.isEnemy())
            target.damage(dmg);
    }
    public void attack(Actor target) {
        if (target != null && target.isEnemy() != this.isEnemy())
            target.damage(dmg);
    }
}
