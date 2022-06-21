package layout.chat;

import client.Client;
import client.LocalCatalog;
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
import muc.Chat;
import muc.Message;
import muc.TextMessage;
import muc.Type;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatC {
    @FXML
    private SplitPane root;

    @FXML
    private ListView<Chat> lvFriends;

    @FXML
    private ScrollPane spChat;

    @FXML
    private VBox vbChatBox;

    @FXML
    private TextField tfMessage;

    @FXML
    private Label chatName;

    @FXML
    private MenuButton btUser;

    //Stage global, um leichter das Fenster schließen zu können.
    // Mit MenuItem ist es schwer, das Stage zu bekommen.
    private static Stage stage;
    private static ChatC controller;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public static ChatC getController() {
        return controller;
    }

    public static void show(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(ChatC.class.getResource("ChatV.fxml"));
            Parent root = (Parent) loader.load();

            controller = (ChatC) loader.getController();


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

        //load Chats from Catalog
        ArrayList<Chat> helper = new ArrayList<>(LocalCatalog.getInstance().selectChatsByUser(Client.user));
        ObservableList<Chat> olC = FXCollections.observableArrayList(helper);
        lvFriends.setItems(olC);
        lvFriends.refresh();
        btUser.setText(Client.user.toString());

        //Chat auswählen
        /*
        lvFriends.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Chat>() {
            @Override
            public void changed(ObservableValue<? extends Chat> observable, Chat oldValue, Chat newValue) {
                chatName.setText(lvFriends.getSelectionModel().getSelectedItem().);
            }
        });
         */
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        if (!tfMessage.getText().isEmpty()) {
            showMessage(tfMessage.getText());
            try {
                Client.sendMsg(tfMessage.getText(), lvFriends.getSelectionModel().getSelectedItem(), Type.STANDARD);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
            tfMessage.setText("");
        }
    }

    private void showMessage(String message) {
        //Datum und Zeit
        LocalDateTime dateTime = LocalDateTime.now();

        Label lbMessage = new Label(message);


        //Datum und Zeit zur Nachricht hinzufügen
        lbMessage.setText(String.format(lbMessage.getText() + "\n\n" + dateTime.format(formatter)));


        lbMessage.getStyleClass().add("user-msg");


        //Wrap Text
        lbMessage.setWrapText(true);
        lbMessage.setMaxWidth(300);


        //Nachrichten auf dem Bildschirm anzeigen
        HBox hbox = new HBox();

        hbox.getChildren().addAll(lbMessage);
        hbox.setAlignment(Pos.CENTER_RIGHT);


        vbChatBox.getChildren().add(hbox);

    }

    @FXML
    private void logout(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void addFriend(ActionEvent event) {
        try {
            AddFriendC.show((Stage) root.getScene().getWindow());
            ArrayList<Chat> helper = new ArrayList<>(Client.user.getChats());
            ObservableList<Chat> olC = FXCollections.observableArrayList(helper);
            lvFriends.setItems(olC);
            lvFriends.refresh();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showIncomingMsg(Message incomingMsg) {


        TextMessage msg = (TextMessage) incomingMsg;

        LocalDateTime date = msg.getTimeStamp();

        Label lbIncoming = new Label("Incoming");
        lbIncoming.setText(msg.getMsg() + "\n\n" + date.format(formatter));
        lbIncoming.getStyleClass().add("friend-msg");
        lbIncoming.setWrapText(true);
        lbIncoming.setMaxWidth(300);
        HBox hbox1 = new HBox();

        hbox1.getChildren().add(lbIncoming);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        vbChatBox.getChildren().add(hbox1);


        //Add msg to Catalog
        LocalCatalog.getInstance().add(incomingMsg);
    }
}


