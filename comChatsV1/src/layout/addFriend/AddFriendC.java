package layout.addFriend;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import muc.ThatsAppException;

import java.io.IOException;

public class AddFriendC {

    @FXML
    private TextField name;

    private static Label lbName = new Label();

    public void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(AddFriendC.class.getResource("AddFriendV.fxml"));
            Parent root = (Parent) loader.load();

            AddFriendC addFriendC = (AddFriendC) loader.getController();

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ThatsApp");
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        lbName.setText("");
    }

    @FXML
    private void add(ActionEvent event) {
        if (!name.getText().isEmpty()) {
            lbName.setText(name.getText());
        }
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public String getText() {
        return lbName.getText();
    }
}