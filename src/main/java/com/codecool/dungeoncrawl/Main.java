package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
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
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    //Label label = new Label("Not clicked");
    Label firstItem = new Label();
    Label inventory = new Label();
    Label damageLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label(" "),0,0);
        ui.add(new Label(" "),0,1);

        ui.add(new Label("Health: "), 0, 2);
        ui.add(healthLabel, 1, 2);

        ui.add(new Label("Damage: "), 0, 3);
        ui.add(damageLabel, 1,3);

        ui.add(new Label(" "),0,4);

        ui.add(new Label("Inventory: "),0, 5);
        ui.add(firstItem, 1,5);

        ui.add(inventory, 1, 6);


        Button button = new Button("Pick up item");

        button.setOnAction(value ->  {
            map.pickUpItem();
            refresh();
        });


        button.setFocusTraversable(false);
        ui.add(button, 0, 0);


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
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        firstItem.setText("" + map.getPlayer().getFirstItem());
        inventory.setText("" + map.getPlayer().getInventory());
        damageLabel.setText("" + map.getPlayer().getDmg());
    }
}
