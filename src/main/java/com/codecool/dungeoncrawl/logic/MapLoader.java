package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class MapLoader {
    private static int counter = 0;
    private static Player player;

    public static GameMap loadMap() {
        String[] maps = {/*"/map.txt", "/map2.txt", "/map3.txt", "/map4.txt",*/ "/map5.txt","/win.txt"};
        InputStream is = MapLoader.class.getResourceAsStream(maps[counter]);
        counter++;
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'i':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            generatePlayer(map, cell);
                            player = map.getPlayer();
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Cow(cell);
                            break;
                        case 'e':
                            cell.setType(CellType.FLOOR);
                            new Ember(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Ghost(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'b':
                            cell.setType(CellType.OPEN_DOOR);
                            break;
                        case 'x':
                            cell.setType(CellType.EXIT);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Potion(cell);
                            break;
                        case 'f':
                            cell.setType(CellType.FAKE_WALL);
                            break;
                        case 'l':
                            cell.setType(CellType.FLOOR);
                            new BFSword(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    private static void generatePlayer(GameMap map, Cell cell) {
        if (Objects.isNull(player)) {
            switch (Main.name) {
                case "Plantel":
                case "Plantelo":
                    map.setPlayer(new Player(cell, 0xf - 0b1110));
                    map.getPlayer().setDmg(0x45);
                    break;
                case "Tokci":
                    map.setPlayer(new Player(cell, 24));
                    map.getPlayer().setArmour(2);
                    map.getPlayer().setDmg(4);
                    break;
                case "Dani":
                    map.setPlayer(new Player(cell, 20));
                    map.getPlayer().setArmour(1);
                    map.getPlayer().setDmg(12);
                    break;
                case "Marci":
                    map.setPlayer(new Player(cell, 1000));
                    map.getPlayer().setArmour(2);
                    break;
                default:
                    map.setPlayer(new Player(cell));
                    break;
            }
        }
        else {
            player.setCell(cell);
            map.setPlayer(player);
        }
    }

    public static void onPlayerDeath() {
        counter = 0;
        player = null;
        Main.setRestartFlag();
    }
}
