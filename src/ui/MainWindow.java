package ui;

import LoLAPI.*;
import Statistics.DataProcessor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * Created by IvanOP on 07.06.2017.
 */
public class MainWindow {
    public static void launch(Stage primaryStage) {
        primaryStage.setTitle("League statistics");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Summoner name:");
        grid.add(userName, 0, 1);

        TextField summonerName = new TextField();
        grid.add(summonerName, 1, 1);

        Label pw = new Label("Champion name:");
        grid.add(pw, 0, 2);

        TextField championName = new TextField();
        grid.add(championName,1,2);


        Button btn = new Button("Get stats");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.BLACK);

            //actiontarget.setText("Summoner id: " + LoLAPI.getSummonerInfo(summonerName.getText()));
            actiontarget.setText(DataProcessor.getKDA(summonerName.getText(), championName.getText()));
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
