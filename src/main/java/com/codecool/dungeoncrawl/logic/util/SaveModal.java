package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.GameStateDaoJdbc;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapSaver;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveModal {
    private static final String title = "Save";
    private static final int width = 300;
    private static final int height = 100;
    private static final String modalText = "Do you want to save?";

    public static void display(Stage stage, GameMap map) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(stage);
        modal.setTitle(title);
        modal.setWidth(width);
        modal.setHeight(height);
        modal.setAlwaysOnTop(true);
        modal.setMaximized(false);
        modal.setResizable(false);

        Label label = new Label();
        label.setText(modalText);
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(label);
        borderPane.setAlignment(label, Pos.CENTER);
        borderPane.setLeft(saveButton);
        borderPane.setAlignment(saveButton, Pos.BOTTOM_LEFT);
        borderPane.setMargin(saveButton, new Insets(5, 5, 5, 5));
        borderPane.setRight(cancelButton);
        borderPane.setAlignment(cancelButton, Pos.BOTTOM_RIGHT);
        borderPane.setMargin(cancelButton, new Insets(5, 5, 5, 5));
        Scene scene = new Scene(borderPane);

        saveButton.setOnAction(e -> {
            GameDatabaseManager gdbm = new GameDatabaseManager();
            Date now = new Date(System.currentTimeMillis());
            PlayerModel pm = new PlayerModel(map.getPlayer());
            GameState gs = new GameState(MapSaver.saveMap(map), now, pm, map.getPlayer().getLevel());
            try {
                gdbm.setup();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            gdbm.savePlayer(pm);
            gdbm.saveGameState(gs);
            modal.close();
        });
        cancelButton.setOnAction(e -> modal.close());

        modal.setScene(scene);
        modal.showAndWait();
    }

}
