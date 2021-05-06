package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FAKE_WALL("fake wall"),
    FLOOR("floor"),
    OPEN_DOOR("open door"),
    DOOR("door"),
    WALL("wall"),
    EXIT("exit");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
