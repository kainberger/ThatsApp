package layout.register;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import layout.chat.ChatC;
import layout.login.LoginC;
import muc.ThatsAppException;
import muc.User;


import java.io.IOException;

public class RegisterC {
    public static boolean err;
    public static String errorMsg;
    @FXML
    private PasswordField tfPassword;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfUsername;

    private static RegisterC controller;

    public static RegisterC getController(){
        return controller;
    }

    private static Stage ownerStage;

    public static void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(RegisterC.class.getResource("RegisterV.fxml"));
            Parent root = (Parent) loader.load();

            controller = (RegisterC) loader.getController();

            Stage stage = new Stage();
            ownerStage = owner;
            err = false;

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ThatsApp");
            stage.getIcons().add(new Image("pictures/fingerpoint.png"));
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showError(String errorMsg){

        Alert alert = new Alert(Alert.AlertType.ERROR,errorMsg);
        alert.showAndWait();
    }

    @FXML
    private void signUp(ActionEvent event) {
        try {

            String email = tfEmail.getText();
            String username = tfUsername.getText();
            String password = tfPassword.getText();

            User user = new User(username,password, email, false);



            Client.register(username,password,email);

         //   if(!UserCatalog.getInstance().addUser(user)) {
                //display Error
          //  }




            //Client.register(username, password, email);


        } catch (ThatsAppException | IOException ex) {
            showError(ex.getMessage());
        }
    }


    public void openChat(){
        ((Stage) (((TextField) tfUsername).getScene().getWindow())).close();
        ownerStage.close();
        ChatC.show();
    }
}
