package layout.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import layout.addFriend.AddFriendC;
import layout.addFriend.Friend;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatC {
    @FXML
    private SplitPane root;

    @FXML
    private Button btUser;

    @FXML
    private ListView<Friend> lvFriends;

    @FXML
    private ScrollPane spChat;

    @FXML
    private VBox vbChatBox;

    @FXML
    private TextField tfMessage;

    //Stage global, um leichter das Fenster schließen zu können.
    // Mit MenuItem ist es schwer, das Stage zu bekommen.
    private static Stage stage;

    public static void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(ChatC.class.getResource("test.fxml"));
            Parent root = (Parent) loader.load();

            ChatC chatC = (ChatC) loader.getController();

            stage = new Stage();

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
    private void initialize() {
        //Auto-scroll nach unten
        spChat.needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spChat.setVvalue(1.0);
            }
        });
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        if (!tfMessage.getText().isEmpty()) {
            showMessage(tfMessage.getText());
            tfMessage.setText("");
        }
    }

    private void showMessage(String message) {
        //Datum und Zeit
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        Label lbMessage = new Label(message);
        Label lbTest = new Label(message);

        //Datum und Zeit zur Nachricht hinzufügen
        lbMessage.setText(lbMessage.getText() + "\n\n" + formatter.format(date));
        lbTest.setText(lbTest.getText() + "\n\n" + formatter.format(date));

        lbMessage.getStyleClass().add("friend-msg");
        lbTest.getStyleClass().add("user-msg");

        //Wrap Text
        lbMessage.setWrapText(true);
        lbMessage.setMaxWidth(300);

        lbTest.setWrapText(true);
        lbTest.setMaxWidth(300);

        //Nachrichten auf dem Bildschirm anzeigen
        HBox hbox = new HBox();

        hbox.getChildren().addAll(lbMessage);
        hbox.setAlignment(Pos.CENTER_LEFT);

        HBox hbox1 = new HBox();

        hbox1.getChildren().add(lbTest);
        hbox1.setAlignment(Pos.CENTER_RIGHT);

        vbChatBox.getChildren().add(hbox);
        vbChatBox.getChildren().add(hbox1);
    }

    @FXML
    private void logout(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void addFriend(ActionEvent event) {
        try {
            AddFriendC.show((Stage) root.getScene().getWindow());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


