package layout.register.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterC {

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
}
