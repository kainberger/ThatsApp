package layout.register.viewController;

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
import layout.register.model.Register;

import java.io.IOException;

public class RegisterC {
    @FXML
    private PasswordField tfPassword;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfUsername;

    private Register model;

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

            model = new Register(email, username, password);

            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
