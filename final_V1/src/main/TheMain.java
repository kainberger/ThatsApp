package main;

import client.Client;
import client.LocalCatalog;
import javafx.application.Application;
import javafx.stage.Stage;
import layout.login.LoginC;

public class TheMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginC.show(primaryStage);
    }

    @Override
    public void init() throws Exception {
        super.init();
        Client.connect();
        LocalCatalog.getInstance().restore();
    }

    @Override
    public void stop() throws Exception {
        Client.stop();
        LocalCatalog.getInstance().persist();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
