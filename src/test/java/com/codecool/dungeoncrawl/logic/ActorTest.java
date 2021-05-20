package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap;

    @BeforeEach
    public void initEach() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void moveUpdatesCells() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 1));
        skeleton.move(1, 0);

        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void setDmg_newDmgCorrect() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        assertEquals(2, skeleton.getDmg());
        skeleton.setDmg(5);
        assertEquals(5, skeleton.getDmg());
    }

    @Test
    void setArmor_newArmorCorrect() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        assertEquals(0, skeleton.getArmour());
        skeleton.setArmour(5);
        assertEquals(5, skeleton.getArmour());
    }

    @Test
    void getArmour_correctArmour() {
        Player player = new Player(gameMap.getCell(1, 1));
        assertEquals(0, player.getArmour());
    }

    @Test
    void setArmour_correctArmour() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setArmour(3);
        assertEquals(3, player.getArmour());
    }

    @Test
    void constructor_withMaxHealth() {
        Player player = new Player(gameMap.getCell(1, 1), 32);
        assertEquals(32, player.getHealth());
        assertEquals(32, player.getMaxHealth());
    }

    @Test
    void onRefresh() {
        Player player = new Player(gameMap.getCell(1, 1), 32);
        player.onRefresh();
    }

    @Test
    void move_onWalkableCell() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 0));
        skeleton.move(0,1,CellType.FLOOR);
        assertEquals(1, skeleton.getX());
        assertEquals(1, skeleton.getY());
    }

    @Test
    void setCell_newCoordinate() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 0));
        Cell cell = gameMap.getCell(1,1);
        skeleton.setCell(cell);
        assertEquals(cell, skeleton.getCell());
    }

    @Test
    void damage_healthLoss() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 0));
        skeleton.damage(4);
        assertEquals(skeleton.getMaxHealth() - 4, skeleton.getHealth());
    }

    @Test
    void die_deathOnHealthLoss() {
        Cell cell = gameMap.getCell(1, 0);
        Skeleton skeleton = new Skeleton(cell);
        skeleton.damage(200);
        assertNull(cell.getActor());
    }

    @Test
    void heal_higherHPAfterHeal() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 0));
        skeleton.damage(6);
        skeleton.heal(2);
        assertEquals(skeleton.getMaxHealth() - 4, skeleton.getHealth());
    }

    @Test
    void isEnemy_playerRecognizeMonsters() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(1, 0));
        assertTrue(skeleton.isEnemy());
    }

    @Test
    void attack_monster() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.attack(skeleton);
        assertEquals(skeleton.getMaxHealth() - player.getDmg(), skeleton.getHealth());
    }

    @Test
    void attack_coordinates() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.attack(1,0);
        assertEquals(skeleton.getMaxHealth() - player.getDmg(), skeleton.getHealth());
    }
}