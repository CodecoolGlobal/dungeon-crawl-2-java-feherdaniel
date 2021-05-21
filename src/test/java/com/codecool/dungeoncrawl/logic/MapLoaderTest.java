package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {
    private final String testMap =  "#############             \n" +
                                    "#...........f    ######## \n" +
                                    "#...s...s...######..g...# \n" +
                                    "#.....k.....b..d........# \n" +
                                    "#...........######...i..# \n" +
                                    "#...........#    #......# \n" +
                                    "#...........#    #...e..# \n" +
                                    "#..........g#    #......# \n" +
                                    "#####..######    ###..### \n" +
                                    "    #..#           #..#   \n" +
                                    "    #..#           #..#   \n" +
                                    "    #..#         ###..### \n" +
                                    "  ###..####      #......# \n" +
                                    "  #.......#      #.i.p..# \n" +
                                    "  #.......#      #......# \n" +
                                    "  #.......#      #......# \n" +
                                    "  #.......#      #...c..# \n" +
                                    "  #.......#      #......# \n" +
                                    "  #...@...#      #......x \n" +
                                    "  #########      ######## \n";

    @Test
     void loadMap_mapTxtFile_gameMap() {
        MapLoader.setPlayerName("");
        InputStream testMap = MapLoader.class.getResourceAsStream("/mapTest.txt");
        GameMap map = MapLoader.loadMap(testMap);
        assertEquals("player", map.getCell(6, 18).getActor().getTileName());

        assertEquals(CellType.EMPTY, map.getCell(0,11).getType());
        assertEquals(CellType.WALL, map.getCell(0,8).getType());
        assertEquals(CellType.FLOOR, map.getCell(1,7).getType());
        assertEquals(CellType.DOOR, map.getCell(15,3).getType());
        assertEquals(CellType.OPEN_DOOR, map.getCell(12,3).getType());
        assertEquals(CellType.FAKE_WALL, map.getCell(12,1).getType());
        assertEquals(CellType.EXIT, map.getCell(24,18).getType());

        assertEquals("skeleton", map.getCell(4,2).getActor().getTileName());
        assertEquals("cow", map.getCell(21,16).getActor().getTileName());
        assertEquals("ghost", map.getCell(20,2).getActor().getTileName());
        assertEquals("ember", map.getCell(21,6).getActor().getTileName());

        assertEquals("key", map.getCell(6,3).getItem().getTileName());
        assertEquals("sword", map.getCell(21,4).getItem().getTileName());
        assertEquals("BFSword", map.getCell(19,13).getItem().getTileName());
        assertEquals("potion", map.getCell(21,13).getItem().getTileName());

        MapLoader.onPlayerDeath();
    }

    @Test
    void loadNextMap_level_correctMapLevel() {
        MapLoader.setPlayerName("Tokci");
        GameMap map = MapLoader.loadNextMap();
        assertEquals("player", map.getCell(6, 18).getActor().getTileName());


        map = MapLoader.loadNextMap();
        assertEquals("player", map.getCell(2, 2).getActor().getTileName());

        MapLoader.onPlayerDeath();
    }

    @Test
    void generatePlayer_playerStatsDependsOnName() {
        MapLoader.setPlayerName("Tokci");
        GameMap map = MapLoader.loadNextMap();
        assertEquals(4, map.getPlayer().getDmg());
        assertEquals(2, map.getPlayer().getArmour());

        MapLoader.onPlayerDeath();
    }

    @Test
    void onPlayerDeath_restartsGame() {
        MapLoader.setPlayerName("Marci");
        GameMap map = MapLoader.loadNextMap();
        map.getPlayer().damage(3);
        MapLoader.onPlayerDeath();
        map = MapLoader.loadNextMap();
        assertEquals(1000 ,map.getPlayer().getHealth());
        MapLoader.onPlayerDeath();
    }

    @Test
    void generatePlayer_cheatsWork() {
        MapLoader.setPlayerName("Dani");
        GameMap map = MapLoader.loadNextMap();
        assertEquals(12, map.getPlayer().getDmg());
        assertEquals(1, map.getPlayer().getArmour());
        MapLoader.onPlayerDeath();
    }

    @Test
    void generatePlayer_cheatsWork2() {
        MapLoader.setPlayerName("Plantel");
        GameMap map = MapLoader.loadNextMap();
        assertEquals(0x45, map.getPlayer().getDmg());
        MapLoader.onPlayerDeath();
    }

    @Test
    void loadGameStateMap_mapString_gameMap() {
        MapLoader mapLoader = new MapLoader();
        MapLoader.setPlayerName("");
        GameMap map = mapLoader.loadGameStateMap(0, testMap);
        assertEquals("player", map.getCell(6, 18).getActor().getTileName());

        assertEquals(CellType.EMPTY, map.getCell(0,11).getType());
        assertEquals(CellType.WALL, map.getCell(0,8).getType());
        assertEquals(CellType.FLOOR, map.getCell(1,7).getType());
        assertEquals(CellType.DOOR, map.getCell(15,3).getType());
        assertEquals(CellType.OPEN_DOOR, map.getCell(12,3).getType());
        assertEquals(CellType.FAKE_WALL, map.getCell(12,1).getType());
        assertEquals(CellType.EXIT, map.getCell(24,18).getType());

        assertEquals("skeleton", map.getCell(4,2).getActor().getTileName());
        assertEquals("cow", map.getCell(21,16).getActor().getTileName());
        assertEquals("ghost", map.getCell(20,2).getActor().getTileName());
        assertEquals("ember", map.getCell(21,6).getActor().getTileName());

        assertEquals("key", map.getCell(6,3).getItem().getTileName());
        assertEquals("sword", map.getCell(21,4).getItem().getTileName());
        assertEquals("BFSword", map.getCell(19,13).getItem().getTileName());
        assertEquals("potion", map.getCell(21,13).getItem().getTileName());

        MapLoader.onPlayerDeath();
    }
}
