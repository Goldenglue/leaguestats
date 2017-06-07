package core;

import javafx.application.Application;
import javafx.stage.Stage;

public class LeagueStats extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ui.MainWindow.launch(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
