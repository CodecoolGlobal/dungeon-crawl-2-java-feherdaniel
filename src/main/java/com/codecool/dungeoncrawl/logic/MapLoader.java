package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
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
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    private static void generatePlayer(GameMap map, Cell cell) {
        switch (Main.name) {
            case "Plantel":
                map.setPlayer(new Player(cell, 0x543 - 0b1010));
                map.getPlayer().setDmg(0b11111111111111111111 - 0xffffe);
                break;
            case "Plantelo":
                map.setPlayer(new Player(cell, 0xf - 0b1110));
                map.getPlayer().setDmg(0x45);
                break;
            case "Tokci":
                map.setPlayer(new Player(cell, 24));
                map.getPlayer().setArmour(20);
                map.getPlayer().setDmg(45000);
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
}
