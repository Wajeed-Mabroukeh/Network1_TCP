package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private control co = new control();

    @Override
    public void start(Stage primaryStage) throws Exception {
        co.initialing();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
