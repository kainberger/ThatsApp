package layout.login;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import layout.chat.ChatC;
import layout.register.RegisterC;
import muc.ThatsAppException;

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


    public static LoginC controller;


    public static LoginC getController() {
        return controller;
    }

    public static void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginC.class.getResource("LoginV.fxml"));
        Parent parent = (Parent) loader.load();

         controller = loader.getController();

        // View aufbauen, konfigurieren, Handler hinzufügen, ...
        controller.init();




        // View anzeigen
        Scene scene = new Scene(parent);
        LoginC.stage = stage;
        LoginC.stage.setTitle("ThatsApp | Login");
        LoginC.stage.getIcons().add(new Image("pictures/fingerpoint.png"));
        LoginC.stage.setScene(scene);
        LoginC.stage.show();



    }


    public void showError(String errorMsg){

        Alert alert = new Alert(Alert.AlertType.ERROR,errorMsg);
        alert.showAndWait();
    }





    private void init() {
    }




    @FXML
    private void login() {

        err = false;

        String errorMsg;



        String userName = username.getText().trim();
        String passwd = password.getText().trim();

        if(userName.length() < 3 || passwd.length() < 8){
            err = true;
             errorMsg = "Username/Passwort prüfen";
             showError(errorMsg);
        }
        else {


            try {

                Client.login(userName, passwd);


            } catch (IOException e) {
                System.err.println("Error no Login");

            } catch (ThatsAppException e) {
                System.err.println(e.getMessage());
            }


            if (err) {
                username.clear();
                password.clear();
                err = false;

            }
        }

    }


    //Chat- und Registrierfenster
    public void openChat() {
        try {
            stage.close();
            ChatC.show();
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