package layout.login.viewController;

import chat.viewController.ChatC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginC {
    @FXML
    private VBox root;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    public static void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginC.class.getResource("LoginV.fxml"));
        Parent parent = (Parent) loader.load();

        LoginC loginC = loader.getController();

        // View aufbauen, konfigurieren, Handler hinzufügen, ...
        loginC.init();

        // View anzeigen
        Scene scene = new Scene(parent);
        stage.setTitle("Personenwartung");
        stage.setScene(scene);
        stage.show();
    }

    private void init() {

    }

    @FXML
    private void showChat(ActionEvent event) {
        try {
            ChatC.show((Stage) root.getScene().getWindow());
        } catch (Exception ex) {
        }
    }
}