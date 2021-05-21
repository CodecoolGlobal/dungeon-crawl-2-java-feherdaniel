package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapSaverTest {

    @Test
    void saveMap_createsTxt() {
        MapLoader.setPlayerName("");
        GameMap map = MapLoader.loadMap("/mapTest.txt");
        MapSaver mapSaver = new MapSaver();
        String testMap =    "#############             \n" +
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
        assertEquals(testMap, mapSaver.saveMap(map));
    }

}
