package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map;

    @BeforeEach
    public void initEach() {
        map = new GameMap(4, 3, CellType.FLOOR);
    }

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbour(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertNull(cell.getNeighbour(0, -1));

        cell = map.getCell(1, 2);
        assertNull(cell.getNeighbour(0, 1));
    }

    @Test
    void constructor_nullGameMap_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new Cell(null, 2, 3, CellType.FLOOR));
    }

    @Test
    void getType_correctType() {
        Cell cell = map.getCell(1, 0);
        assertEquals(CellType.FLOOR, cell.getType());
    }

    @Test
    void setType_correctType() {
        GameMap doorMap = new GameMap(3, 3, CellType.FLOOR);
        Cell cell = doorMap.getCell(1, 1);
        cell.setType(CellType.DOOR);
        assertEquals(CellType.DOOR, cell.getType());
    }

    @Test
    void getActor_noActorOnCell_null() {
        Cell cell = map.getCell(1, 0);
        assertNull(cell.getActor());
    }

    @Test
    void setActor_actorOnCell_actor() {
        Cell cell = map.getCell(1, 0);
        Player player = new Player(cell, 34);
        cell.setActor(player);
        assertEquals(player, cell.getActor());
    }

    @Test
    void getItem_noItemOnCell_null() {
        Cell cell = map.getCell(1, 0);
        assertNull(cell.getActor());
    }

    @Test
    void setItem_itemOnCell_item() {
        Cell cell = map.getCell(1, 0);
        Sword sword = new Sword(cell);
        cell.setItem(sword);
        assertEquals(sword, cell.getItem());
    }

    @Test
    void getTileName_floorTile() {
        Cell cell = map.getCell(1, 0);
        assertEquals("floor", cell.getTileName());
    }

    @Test
    void getMapParams_correctWidthAndHeight() {
        Cell cell = map.getCell(1, 0);
        assertEquals(4, cell.getMapParams()[0]);
        assertEquals(3, cell.getMapParams()[1]);
    }
}