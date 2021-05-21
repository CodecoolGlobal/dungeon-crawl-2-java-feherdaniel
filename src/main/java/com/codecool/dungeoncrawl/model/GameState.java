package com.codecool.dungeoncrawl.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private String currentMap;
    private static int level;
    private PlayerModel player;

    public GameState(String currentMap, Date savedAt, PlayerModel player, int level) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
        this.level = level;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel(int level) {
        this.level = level;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }
}
