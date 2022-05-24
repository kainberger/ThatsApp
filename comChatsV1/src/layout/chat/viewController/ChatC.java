package layout.chat.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.omg.PortableServer.POA;

import java.io.IOException;

public class ChatC {
    @FXML
    private SplitPane root;

    @FXML
    private Button btUser;

    @FXML
    private ListView<?> lvFriends;

    @FXML
    private ScrollPane spChat;

    @FXML
    private VBox vbChatBox;

    @FXML
    private TextField tfMessage;

    public static void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(ChatC.class.getResource("ChatV.fxml"));
            Parent root = (Parent) loader.load();

            ChatC chatC = (ChatC) loader.getController();

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Chat");
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        spChat.needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spChat.setVvalue(1.0);
            }
        });
    }

    @FXML
    private void test(ActionEvent event) {
        System.out.println("asdf");
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        if (!tfMessage.getText().isEmpty()) {
            showMessage(tfMessage.getText());
            tfMessage.setText("");
        }
    }

    private void showMessage(String message) {
        Label lbMessage = new Label(message);
        Label lbTest = new Label(message);

        lbMessage.setAlignment(Pos.CENTER_RIGHT);
        lbTest.setAlignment(Pos.CENTER_LEFT);

        vbChatBox.getChildren().add(lbMessage);
        vbChatBox.getChildren().add(lbTest);
    }
}
