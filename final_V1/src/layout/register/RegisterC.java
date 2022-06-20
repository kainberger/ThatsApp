package layout.register;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import muc.User;
import server.Server;
import server.UserCatalog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class RegisterC {
    @FXML
    private PasswordField tfPassword;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfUsername;

    public static void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(RegisterC.class.getResource("RegisterV.fxml"));
            Parent root = (Parent) loader.load();

            RegisterC registerC = (RegisterC) loader.getController();

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ThatsApp");
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void signUp(ActionEvent event) {
        try {
            String email = tfEmail.getText();
            String username = tfUsername.getText();
            String password = tfPassword.getText();

            User user = new User(username, password, email, false); //TODO: later ==> pw as hash

            if(!UserCatalog.getInstance().addUser(user)) {
                //display Error
            }

            //Client.register(username, password, email);

            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
