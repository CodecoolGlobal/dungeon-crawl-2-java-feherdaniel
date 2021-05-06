package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    final static int maxWidth = 12;
    Canvas canvas = new Canvas(
            Math.min(map.getWidth() * Tiles.TILE_WIDTH, (viewDeltaH * 2 + 1) * Tiles.TILE_WIDTH),
            Math.min(map.getHeight() * Tiles.TILE_WIDTH, (viewDeltaV * 2 + 1) * Tiles.TILE_WIDTH));
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label firstItem = new Label();
    Label inventory = new Label();
    Label damageLabel = new Label();
    Label nameLabel = new Label();
    public static String name;
    final static int viewDeltaH = 12;
    final static int viewDeltaV = 9;
    static boolean restartFlag = false;
    public static void setRestartFlag() { restartFlag = true; }

    public static void main(String[] args) {
        if (args.length > 0) name = args[0];
        else name = "Player";
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        nameLabel.setText(name);
        ui.add(new Label("Name: "), 0, 0);
        ui.add(nameLabel, 1, 0);

        ui.add(new Label(" "),0,1);

        Button button = new Button("Pick up item");

        button.setOnAction(value ->  {
            map.pickUpItem();
            refresh();
        });


        button.setFocusTraversable(false);
        ui.add(button, 0, 2);

        ui.add(new Label(" "),0,2);
        ui.add(new Label(" "),0,3);

        ui.add(new Label("Health: "), 0, 4);
        ui.add(healthLabel, 1, 4);


        ui.add(new Label("Damage: "), 0, 5);
        ui.add(damageLabel, 1,5);

        ui.add(new Label(" "),0,6);

        ui.add(new Label("Inventory: "),0, 7);
        ui.add(firstItem, 1,7);

        ui.add(inventory, 1, 8);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                map.getPlayer().attack(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                map.getPlayer().attack(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                map.getPlayer().attack(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                map.getPlayer().attack(1, 0);
                refresh();
                break;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++)
                if (map.getCell(i, j).getActor() != null)
                    actors.add(map.getCell(i, j).getActor());
        for (Actor a : actors)
            a.onRefresh();

        int minX = Math.max(map.getPlayer().getX() - viewDeltaH, 0);
        int minY = Math.max(map.getPlayer().getY() - viewDeltaV, 0);
        int maxX = Math.min(map.getWidth(), map.getPlayer().getX() + viewDeltaH);
        int maxY = Math.min(map.getHeight(), map.getPlayer().getY() + viewDeltaV);

        if (minX == 0) maxX = Math.min(2 * viewDeltaH + 1, map.getWidth() - 1);
        if (minY == 0) maxY = Math.min(2 * viewDeltaV + 1, map.getHeight() - 1);
        if (maxX == map.getWidth() - 1) minX = Math.max(map.getWidth() - 2 - viewDeltaH  * 2, 0);
        if (maxY == map.getHeight() - 1) minY = Math.max(map.getHeight() - 2 - viewDeltaV * 2, 0);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - minX, y -minY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(),x - minX, y -minY);
                } else {
                    Tiles.drawTile(context, cell,x - minX, y -minY);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth() + "/" + map.getPlayer().getMaxHealth());
        firstItem.setText("" + map.getPlayer().getFirstItem());
        inventory.setText("" + map.getPlayer().getInventory());
        damageLabel.setText("" + map.getPlayer().getDmg());

        if (map.nextLevel() || restartFlag) {
            restartFlag = false;
            map = MapLoader.loadMap();
            refresh();
        }
    }
}
