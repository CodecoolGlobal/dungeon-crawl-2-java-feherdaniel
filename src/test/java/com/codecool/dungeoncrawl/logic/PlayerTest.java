package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    GameMap gameMap;

    @BeforeEach
    public void initEach() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void getName_notInitializedPlayerName_null() {
        Player player = new Player(gameMap.getCell(1, 1));
        assertNull(player.getName());
    }

    @Test
    void getName_addNewPlayerName_playerName() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setName("DragonHunter");
        assertEquals("DragonHunter",player.getName());
    }

    @Test
    void getFirstItem_emptyInventory_null() {
        Player player = new Player(gameMap.getCell(1, 1));
        assertEquals(" ", player.getFirstItem());
    }

    @Test
    void addToInventory_addSwordToInventory_sword() {
        Cell cell = gameMap.getCell(1, 1);
        Player player = new Player(cell);
        Sword sword = new Sword(cell);
        player.addToInventory(sword);
        assertEquals("sword", player.getFirstItem());
    }

    @Test
    void addToInventory_addPotionToInventory_heals() {
        Cell cell = gameMap.getCell(1, 1);
        Player player = new Player(cell);
        player.damage(6);
        Potion potion = new Potion(cell);
        player.addToInventory(potion);
        assertEquals(9,player.getHealth());
    }

    @Test
    void getInventory_fewItemsInInventory_inventoryList() {
        Cell cell = gameMap.getCell(1, 1);
        Cell swordCell = gameMap.getCell(1,2);
        Player player = new Player(cell);
        Key key = new Key(cell);
        player.addToInventory(key);
        // There is a bug in the game the first item picked up 2x
        Key key2 = new Key(swordCell);
        player.addToInventory(key2);
        Sword sword = new Sword(swordCell);
        player.addToInventory(sword);
        assertEquals("key\n" + "sword\n", player.getInventory());
    }

    @Test
    void getTileName_returnsPlayerTile() {
        Player player = new Player(gameMap.getCell(1, 1));
        assertEquals("player", player.getTileName());
    }

    @Test
    void die_deathOnHealthLoss() {
        Cell cell = gameMap.getCell(1, 0);
        Player player = new Player(cell);
        player.damage(200);
        assertNull(cell.getActor());
    }

    @Test
    void move_unlocksDoorWithKey() {
        Cell cell = gameMap.getCell(1, 1);
        Player player = new Player(cell);
        Key key = new Key(cell);
        player.addToInventory(key);
        Cell doorCell = gameMap.getCell(1,2);
        doorCell.setType(CellType.DOOR);
        player.move(0,1);
        assertEquals(" ", player.getFirstItem());
        assertEquals(doorCell, player.getCell());
    }
}
