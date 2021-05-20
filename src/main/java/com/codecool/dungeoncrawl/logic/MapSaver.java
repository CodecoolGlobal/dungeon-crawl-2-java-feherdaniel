package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.BFSword;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Potion;
import com.codecool.dungeoncrawl.logic.items.Sword;

public class MapSaver {

    public String saveMap(GameMap gameMap) {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                    Cell cell = gameMap.getCell(x, y);
                    switch (cell.getType()) {
                        case WALL:
                            sb.append("#");
                            break;
                        case FLOOR:
                            if (cell.getActor() instanceof Skeleton) sb.append("s");
                            else if (cell.getActor() instanceof Cow) sb.append("c");
                            else if (cell.getActor() instanceof Ember) sb.append("e");
                            else if (cell.getActor() instanceof Ghost) sb.append("g");
                            else if (cell.getActor() instanceof Player) sb.append("@");
                            else if (cell.getItem() instanceof Sword) sb.append("i");
                            else if (cell.getItem() instanceof Key) sb.append("k");
                            else if (cell.getItem() instanceof Potion) sb.append("p");
                            else if (cell.getItem() instanceof BFSword) sb.append("l");
                            else sb.append(".");
                            break;
                        case DOOR:
                            sb.append("d");
                            break;
                        case OPEN_DOOR:
                            sb.append("b");
                            break;
                        case EXIT:
                            sb.append("x");
                            break;
                        case FAKE_WALL:
                            sb.append("f");
                            break;
                        default:
                            sb.append(" ");
                            break;
                    }
                }
            sb.append("\n");
            }
        return sb.toString();
    }
}
