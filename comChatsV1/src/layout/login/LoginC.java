package layout.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import layout.chat.ChatC;
import layout.register.RegisterC;

import java.io.IOException;

public class LoginC {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public static void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginC.class.getResource("LoginV.fxml"));
        Parent parent = (Parent) loader.load();

        LoginC loginC = loader.getController();

        // View aufbauen, konfigurieren, Handler hinzufügen, ...
        loginC.init();

        // View anzeigen
        Scene scene = new Scene(parent);
        stage.setTitle("ThatsApp");
        stage.setScene(scene);
        stage.show();
    }

    private void init() {

    }

    @FXML
    private void login(ActionEvent actionEvent) {
        openChat();
    }

    //Chat- und Registrierfenster
    private void openChat() {
        try {
            ChatC.show((Stage) root.getScene().getWindow());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void openRegister(ActionEvent event) {
        try {
            RegisterC.show((Stage) root.getScene().getWindow());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}