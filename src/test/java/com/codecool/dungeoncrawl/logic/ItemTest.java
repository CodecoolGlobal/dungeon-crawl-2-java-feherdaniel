package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.BFSword;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    GameMap gameMap;

    @BeforeEach
    public void initEach() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void getCell_cell() {
        Cell cell = gameMap.getCell(1, 1);
        Sword sword = new Sword(cell);
        assertEquals(cell, sword.getCell());
    }

    @Test
    void getTileName_returnsMonsterName() {
        BFSword bfSword = new BFSword(gameMap.getCell(1, 1));
        assertEquals("BFSword", bfSword.getTileName());
        Key key = new Key(gameMap.getCell(1, 1));
        assertEquals("key", key.getTileName());
        Potion potion = new Potion(gameMap.getCell(1, 1));
        assertEquals("potion", potion.getTileName());
        Sword sword = new Sword(gameMap.getCell(1, 1));
        assertEquals("sword", sword.getTileName());
    }

    @Test
    void BFSword_getDamage_42() {
        BFSword bfSword = new BFSword(gameMap.getCell(1, 1));
        assertEquals(42, bfSword.getDamage());
    }
}
