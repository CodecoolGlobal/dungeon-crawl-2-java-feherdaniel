package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.model.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoadModal {
    private static final String title = "Load";
    private static final int width = 300;
    private static final int height = 40;
    public static String mapLoad = "";
    public static int level = 0;

    public static void loadMap(Stage stage, GameMap map) {
        GameMap loadedMap;
        GameDatabaseManager gdbm = new GameDatabaseManager();
        try {
            gdbm.setup();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        var allStates = gdbm.loadAllGameStates();
        var allPlayers = gdbm.loadAllPlayers();

        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle(title);
        modal.setWidth(width);
        modal.setHeight(height*allStates.size());
        modal.setAlwaysOnTop(true);
        modal.setMaximized(false);
        modal.setResizable(false);

        Label label = new Label();
        label.setText("Do you want to build a snowman");
        Button loadButton = new Button("Load");
        Button cancelButton = new Button("Cancel");
        GridPane gridPane = new GridPane();
        gridPane.setPrefWidth(200);
        gridPane.setPadding(new Insets(10));
        Label idHead = new Label("ID");
        Label dateHead = new Label("Date");
        Label playerId = new Label("Player id");
        gridPane.add(idHead, 1, 0);
        gridPane.add(dateHead, 2, 0);
        gridPane.add(playerId, 8, 0);
        for (int i=0; i < allStates.size(); i++) {
            Button id = new Button(String.valueOf(allStates.get(i).getId()));
            Label date = new Label(String.valueOf(allStates.get(i).getSavedAt()));
            Label player = new Label(String.valueOf(allStates.get(i).getPlayer().getId()));
            Label space = new Label("         ");
            gridPane.add(id, 1, i+1 );
            gridPane.add(date, 2, i+1 );
            gridPane.add(space, 3, i+1);
            gridPane.add(player, 8, i+1 );
            var state = gdbm.loadGameState(allStates.get(i).getId());
            var newPlayer = gdbm.getPlayer(allStates.get(i).getId());
            id.setOnAction(e -> {
                MapLoader mp = new MapLoader();
//                Main main = new Main();
                mapLoad = state.getCurrentMap();
                level = state.getLevel();
//                main.setMap(mp.loadGameStateMap(state.getLevel(), state.getCurrentMap()));
//                main.refresh();
                modal.close();
            });
        }
        gridPane.add(cancelButton, 1, allStates.size()+1);
        Scene scene = new Scene(gridPane);

        cancelButton.setOnAction(e -> modal.close());

        modal.setScene(scene);
        modal.showAndWait();
    }}
