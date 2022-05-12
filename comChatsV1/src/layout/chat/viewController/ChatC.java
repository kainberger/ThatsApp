package layout.chat.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatC {
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
}
