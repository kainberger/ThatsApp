package layout.login;

import client.Client;
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
import muc.ThatsAppException;
import muc.User;
import server.UserCatalog;

import java.io.IOException;

public class LoginC {
    @FXML
    private AnchorPane root;

    @FXML
    private TextField username;

    @FXML
    private TextField password;
    static Stage stage;

    public static void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginC.class.getResource("LoginV.fxml"));
        Parent parent = (Parent) loader.load();

        LoginC loginC = loader.getController();

        // View aufbauen, konfigurieren, Handler hinzufügen, ...
        loginC.init();

        // View anzeigen
        Scene scene = new Scene(parent);
        LoginC.stage = stage;
        LoginC.stage.setTitle("ThatsApp");
        LoginC.stage.setScene(scene);
        LoginC.stage.show();
    }

    private void init() {

    }

    @FXML
    private void login(ActionEvent actionEvent) throws ThatsAppException {
        if(loginValidaion()) {
            //TODO: connect to client
            Client.main(new String[]{username.getText().trim(), password.getText()});
            LoginC.stage.close();
            openChat();
        } else {
            System.err.println("no login");
            //displayError
        }
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

    private boolean loginValidaion() throws ThatsAppException {
        if(UserCatalog.getInstance().getUserbyName(username.getText().trim()) != null) {
            User userIn = UserCatalog.getInstance().getUserbyName(username.getText().trim());
            return userIn.getPasswordHash().equals(User.hashPassword(password.getText()));
        }
        else
            return false;
    }
}