package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int maxHp;
    private int x;
    private int y;
    private String inventory;
    private int level;

    public PlayerModel(String playerName, int hp, int x, int y, String inventory, int maxHp) {
        this.playerName = playerName;
        this.maxHp = maxHp;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.inventory = inventory;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.inventory = player.getInventory();
        this.maxHp = player.getMaxHealth();
        this.hp = player.getHealth();
        this.level = player.getLevel();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() { return maxHp; }

    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() { return level; }

    public void setY(int y) {
        this.y = y;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }
}
