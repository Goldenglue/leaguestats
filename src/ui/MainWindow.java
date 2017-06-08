package ui;

import LoLAPI.StaticData;
import Statistics.DataProcessor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.PrettyPrinter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by IvanOP on 07.06.2017.
 */
public class MainWindow {
    private static ImageView imageView = new ImageView();
    private static ImageView item0 = new ImageView();
    private static ImageView item1 = new ImageView();
    private static ImageView item2 = new ImageView();
    private static ImageView item3 = new ImageView();
    private static ImageView item4 = new ImageView();
    private static ImageView item5 = new ImageView();
    private static ImageView item6 = new ImageView();
    private static Text win;


    public static void launch(Stage primaryStage) {
        primaryStage.setTitle("League statistics");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("LoL Stats");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Summoner name:");
        grid.add(userName, 0, 1);

        TextField summonerName = new TextField();
        grid.add(summonerName, 1, 1,2,1);

        Label pw = new Label("Champion name:");
        grid.add(pw, 0, 2);

        TextField championName = new TextField();
        grid.add(championName, 1, 2,2,1);

        Button btn = new Button("Get stats");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 0, 3);

        win = new Text();
        grid.add(win, 0,8);

        final Text kills = new Text();
        grid.add(kills, 1,8);

        final Text deaths = new Text();
        grid.add(deaths, 2,8);

        final Text assists = new Text();
        grid.add(assists, 0,9);

        final Text totalDamageDealtToChampions = new Text();
        grid.add(totalDamageDealtToChampions, 1,9);

        final Text magicDamageDealtToChampions = new Text();
        grid.add(magicDamageDealtToChampions, 2,9);

        final Text trueDamageDealtToChampions = new Text();
        grid.add(trueDamageDealtToChampions, 0,10);

        final Text visionScore = new Text();
        grid.add(visionScore, 1,10);

        final Text goldEarned = new Text();
        grid.add(goldEarned, 2,10);

        final Text totalMinionsKilled = new Text();
        grid.add(totalMinionsKilled, 0,11);

        final Text neutralMinionsKilled = new Text();
        grid.add(neutralMinionsKilled, 1,11);

        final Text neutralMinionsKilledTeamJungle = new Text();
        grid.add(neutralMinionsKilledTeamJungle, 2,11);

        final Text neutralMinionsKilledEnemyJungle = new Text();
        grid.add(neutralMinionsKilledEnemyJungle, 0,12);

        final Text wardsPlaced = new Text();
        grid.add(wardsPlaced, 1,12);

        btn.setOnAction(e -> {
            setChampionImage(championName.getText());
            actionTarget.setFill(Color.BLACK);
            Map<String, String> info = DataProcessor.getInfo(summonerName.getText(), championName.getText());
            setItemsFromIds(info);
            actionTarget.setText("KDA is: " + info.get("kda"));
            win.setText("Win: " + info.get("win"));
            kills.setText("Kills: " + info.get("kills"));
            deaths.setText("Deaths: " + info.get("deaths"));
            assists.setText("Assists: " + info.get("assists"));
            totalDamageDealtToChampions.setText("Total damage dealt to champions: " + info.get("totalDamageDealtToChampions"));
            magicDamageDealtToChampions.setText("Magic damage dealt to champions: " + info.get("magicDamageDealtToChampions"));
            trueDamageDealtToChampions.setText("True Damage dealt to champions: " + info.get("trueDamageDealtToChampions"));
            visionScore.setText("Vision score: " + info.get("visionScore"));
            goldEarned.setText("Gold earned: " + info.get("goldEarned"));
            totalMinionsKilled.setText("Minions killed: " + info.get("totalMinionsKilled"));
            neutralMinionsKilled.setText("Neutral minions killed: " + info.get("neutralMinionsKilled"));
            neutralMinionsKilledTeamJungle.setText("Neutral minions killed from team jungle: " + info.get("neutralMinionsKilledTeamJungle"));
            neutralMinionsKilledEnemyJungle.setText("Neutral minions killed from enemy jungle: " + info.get("neutralMinionsKilledEnemyJungle"));
            wardsPlaced.setText("Wards placed: " + info.get("wardsPlaced"));

        });

        grid.add(imageView, 0, 4);
        grid.add(item0,0,5);
        grid.add(item1,1,5);
        grid.add(item2,2,5);
        grid.add(item3,0,6);
        grid.add(item4,1,6);
        grid.add(item5,2,6);
        grid.add(item6,0,7);

        Scene scene = new Scene(grid, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void setItemsFromIds(Map<String, String> info) {
        item0.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item0") + ".png"));
        item1.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item1") + ".png"));
        item2.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item2") + ".png"));
        item3.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item3") + ".png"));
        item4.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item4") + ".png"));
        item5.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item5") + ".png"));
        item6.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + info.get("item6") + ".png"));

    }

    private static void setChampionImage(String championName) {
        imageView.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/" + championName + ".png"));
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }
}
