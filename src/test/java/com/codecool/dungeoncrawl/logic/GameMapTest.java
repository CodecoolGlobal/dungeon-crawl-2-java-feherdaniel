package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    GameMap gameMap;

    @BeforeEach
    public void initEach() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void setPlayer_changesPlayer() {
        assertNull(gameMap.getPlayer());
        Player player = new Player(gameMap.getCell(1, 1));
        gameMap.setPlayer(player);
        assertEquals(player, gameMap.getPlayer());
    }

    @Test
    void pickUpItem_playerPicksUpItem() {
        Cell cell = gameMap.getCell(1, 1);
        Player player = new Player(cell);
        Sword sword = new Sword(cell);
        gameMap.setPlayer(player);
        gameMap.pickUpItem();
        assertEquals("sword",player.getFirstItem());
    }

    @Test
    void pickUpItem_itemDisappearsFromMap() {
        Cell cell = gameMap.getCell(1, 1);
        Player player = new Player(cell);
        Sword sword = new Sword(cell);
        gameMap.setPlayer(player);
        gameMap.pickUpItem();
        assertNull(cell.getItem());
    }

    @Test
    void nextLevel_playerCanGoToTheNextLevel() {
        Cell cell = gameMap.getCell(1, 1);
        cell.setType(CellType.EXIT);
        Player player = new Player(cell);
        gameMap.setPlayer(player);
        assertTrue(gameMap.nextLevel());
    }

    @Test
    void nextLevel_playerStaysOnTheCurrentLevelWhenNotStandingOnExit() {
        Cell cell = gameMap.getCell(1, 1);
        Cell door = gameMap.getCell(1,2);
        door.setType(CellType.EXIT);
        Player player = new Player(cell);
        gameMap.setPlayer(player);
        assertFalse(gameMap.nextLevel());
    }
}
