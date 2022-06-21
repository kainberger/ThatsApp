package layout.chat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatC {
    @FXML
    private SplitPane root;

    @FXML
    private ListView<String> lvFriends;

    @FXML
    private ScrollPane spChat;

    @FXML
    private VBox vbChatBox;

    @FXML
    private TextField tfMessage;

    @FXML
    private Label chatName;

    //Leichtere Kommunikation, um die Freunde hinzuzufügen
    private AddFriendC addFriendC = new AddFriendC();

    private ObservableList<String> olFriends = FXCollections.observableArrayList();

    //Stage global, um leichter das Fenster schließen zu können.
    // Mit MenuItem ist es schwer, das Stage zu bekommen.
    private static Stage stage;

    public static void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(ChatC.class.getResource("ChatV.fxml"));
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
        lvFriends.setItems(olFriends);

        //Auto-scroll nach unten
        spChat.needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spChat.setVvalue(1.0);
            }
        });

        //Chat auswählen
        lvFriends.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selected = lvFriends.getSelectionModel().getSelectedItem();
                setup(selected);
            }
        });
    }

    /**
     * Chat vom ausgewählten Freund/Chat anzeigen
     * @param name
     */
    private void setup(String name) {
        chatName.setText(name);
    }

    /**
     * Nachricht senden
     * @param event
     */
    @FXML
    private void sendMessage(ActionEvent event) {
        if (!tfMessage.getText().isEmpty() && !chatName.getText().equals("Select a friend first")) {
            showMessage(tfMessage.getText());
            tfMessage.setText("");
        }
    }

    /**
     * gesendete Nachricht am Bildschirm anzeigen lassen
     * @param message
     */
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

    /**
     * Scene ,für Freunde/Chats hinzufügen, öffen
     * @param event
     */
    @FXML
    private void openFriendScene(ActionEvent event) {
        try {
            addFriendC.show((Stage) root.getScene().getWindow());
            addToFriendList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add Friend/Chat to the List
     */
    private void addToFriendList() {
        if (!olFriends.contains(addFriendC.getText()) && !addFriendC.getText().isEmpty()) {
            olFriends.add(addFriendC.getText());
        }
    }

    /**
     * Ausloggen
     * @param event
     */
    @FXML
    private void logout(ActionEvent event) {
        stage.close();
    }
}


