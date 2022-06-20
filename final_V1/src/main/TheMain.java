package main;

import javafx.application.Application;
import javafx.stage.Stage;
import layout.login.LoginC;

public class TheMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginC.show(primaryStage);
    }
}
