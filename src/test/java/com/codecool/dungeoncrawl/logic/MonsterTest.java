package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {
    GameMap gameMap;

    @BeforeEach
    public void initEach() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void getTileName_returnsMonsterName() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 1));
        assertEquals("skeleton", skeleton.getTileName());
        Cow cow = new Cow(gameMap.getCell(1, 1));
        assertEquals("cow", cow.getTileName());
        Ember ember = new Ember(gameMap.getCell(1, 1));
        assertEquals("ember", ember.getTileName());
        Ghost ghost = new Ghost(gameMap.getCell(1, 1));
        assertEquals("ghost", ghost.getTileName());
    }

    @Test
    void skeleton_onRefresh_moves() {
        GameMap bigMap = new GameMap(100, 100, CellType.FLOOR);
        Cell cell = bigMap.getCell(1, 1);
        Skeleton skeleton = new Skeleton(cell);
        for (int i = 0; i < 100; i++) {
            skeleton.onRefresh();
        }
        assertNull(cell.getActor());
    }

    @Test
    void skeleton_onRefresh_attacks() {
        Cell cell = gameMap.getCell(1, 1);
        Cell playerCell = gameMap.getCell(1, 2);
        Player player = new Player(playerCell);
        Skeleton skeleton = new Skeleton(cell);
        skeleton.onRefresh();
        assertEquals(player.getMaxHealth() - skeleton.getDmg(), player.getHealth());
    }

    @Test
    void ghost_onRefresh_moves() {
        GameMap bigMap = new GameMap(100, 100, CellType.FLOOR);
        Cell cell = bigMap.getCell(1, 1);
        Ghost ghost = new Ghost(cell);
        for (int i = 0; i < 100; i++) {
            ghost.onRefresh();
        }
        assertNull(cell.getActor());
    }

    @Test
    void ghost_onRefresh_attacks() {
        GameMap bigMap = new GameMap(100, 100, CellType.FLOOR);
        Cell cell = bigMap.getCell(1, 1);
        Cell playerCell = bigMap.getCell(1, 2);
        Ghost ghost = new Ghost(cell);
        Player player = new Player(playerCell);
        ghost.onRefresh();
        assertEquals(player.getMaxHealth() - ghost.getDmg(), player.getHealth());
    }

    @Test
    void ember_onRefresh_moves() {
        GameMap bigMap = new GameMap(100, 100, CellType.EMPTY);
        for (int i = 30; i < 70; i++) {
            for (int j = 30; j < 70; j++) {
                Cell cell = bigMap.getCell(i, j);
                cell.setType(CellType.FLOOR);
            }
        }
        Cell cell = bigMap.getCell(50, 50);
        Ember ember = new Ember(cell);
        for (int i = 0; i < 100; i++) {
            ember.onRefresh();
        }
        assertNull(cell.getActor());
    }

    @Test
    void ember_onRefresh_attacks() {
        GameMap bigMap = new GameMap(100, 100, CellType.EMPTY);
        for (int i = 30; i < 70; i++) {
            for (int j = 30; j < 70; j++) {
                Cell cell = bigMap.getCell(i, j);
                cell.setType(CellType.FLOOR);
            }
        }
        Cell cell = bigMap.getCell(50, 50);
        Cell playerCell = bigMap.getCell(51, 50);
        Ember ember = new Ember(cell);
        Player player = new Player(playerCell);
        ember.onRefresh();
        assertEquals(player.getMaxHealth() - ember.getDmg(), player.getHealth());
    }

    @Test
    void cow_onRefresh_doNotAttackOnMaxHealth() {
        Cell cell = gameMap.getCell(1, 1);
        Cell playerCell = gameMap.getCell(1, 2);
        Player player = new Player(playerCell);
        Cow cow = new Cow(cell);
        cow.onRefresh();
        assertEquals(player.getMaxHealth(), player.getHealth());
    }

    @Test
    void cow_onRefresh_killsYouWhenDamaged() {
        Cell cell = gameMap.getCell(1, 1);
        Cell playerCell = gameMap.getCell(1, 2);
        Player player = new Player(playerCell);
        Cow cow = new Cow(cell);
        cow.damage(1);
        cow.onRefresh();
        assertEquals(player.getMaxHealth() - cow.getDmg(), player.getHealth());
    }
}
