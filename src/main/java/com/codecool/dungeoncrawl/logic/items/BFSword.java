package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class BFSword extends Sword{

        private final int damage = 42;

        public BFSword(Cell cell) {super(cell);}

        public String getTileName() {
            return "BFSword";
        }

        public int getDamage() {
            return damage;
        }


}
