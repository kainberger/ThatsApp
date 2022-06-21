package layout.login;

import client.Client;
import client.ClientReceiverThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    @FXML
    void login(ActionEvent event) {
        login();
    }
    static Stage stage;
    public static Boolean err;
    public static String errorMsg;

    public static void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginC.class.getResource("LoginV.fxml"));
        Parent parent = (Parent) loader.load();

        LoginC loginC = loader.getController();

        err = false;
        errorMsg = "";
        // View anzeigen
        Scene scene = new Scene(parent);
        LoginC.stage = stage;
        LoginC.stage.setTitle("ThatsApp");
        LoginC.stage.setScene(scene);
        LoginC.stage.show();



    }

    @FXML
    private void login() {

        String userName = username.getText().trim();
        String passwd = password.getText().trim();


        try {
            Client.login(userName,passwd);

        } catch (IOException e) {
            System.err.println("Error no Login");
            //TODO: Alert
        } catch (ThatsAppException e) {
            System.err.println(e.getMessage());
        }

        //TODO: LOGIN
        if (err) {
            username.clear();
            password.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING, errorMsg);
            alert.show();
            err = false;
        }
        else {
            LoginC.stage.close();
            openChat();
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

    /*private boolean loginValidaion() throws ThatsAppException {
        if(UserCatalog.getInstance().getUserbyName(username.getText().trim()) != null) {
            User userIn = UserCatalog.getInstance().getUserbyName(username.getText().trim());
            return userIn.getPasswordHash().equals(User.hashPassword(password.getText()));
        }
        else
            return false;
    }*/


}