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
import java.util.Set;


/**
 * Created by IvanOP on 07.06.2017.
 */
public class MainWindow {
    //private static Image square1 = new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/Aatrox.png");
    //private static Image square2 = new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/Vi.png");
    static ImageView imageView = new ImageView();
    static ImageView item0 = new ImageView();
    static ImageView item1 = new ImageView();
    static ImageView item2 = new ImageView();
    static ImageView item3 = new ImageView();
    static ImageView item4 = new ImageView();
    static ImageView item5 = new ImageView();
    static ImageView item6 = new ImageView();
    static Set<ImageView> setOfItemsImages = new HashSet<>();
    static {
        setOfItemsImages.add(item0);
        setOfItemsImages.add(item1);
        setOfItemsImages.add(item2);
        setOfItemsImages.add(item3);
        setOfItemsImages.add(item4);
        setOfItemsImages.add(item5);
        setOfItemsImages.add(item6);

    }


    public static void launch(Stage primaryStage) {


        primaryStage.setTitle("League statistics");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
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

        btn.setOnAction(e -> {
            setChampionImage(championName.getText());
            actionTarget.setFill(Color.BLACK);
            //actionTarget.setText("KDA is: " + DataProcessor.getInfo(summonerName.getText(), championName.getText()));
            actionTarget.setText("some very long text");

        });

        grid.add(imageView, 0, 4);
        grid.add(item0,0,5);
        grid.add(item1,1,5);
        grid.add(item2,2,5);
        grid.add(item3,0,6);
        grid.add(item4,1,6);
        grid.add(item5,2,6);
        grid.add(item6,2,6);


        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void setItemsFromIds(String[] imageId) {
        int id = 0;
        for (ImageView item : setOfItemsImages) {
            item.setImage(new Image("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + imageId[id++]));
            item.setPreserveRatio(true);
            item.setSmooth(true);
            item.setCache(true);
        }

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
