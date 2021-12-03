package TCP;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Control_TCP co = new Control_TCP();

    public Main() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        co.initialing();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
