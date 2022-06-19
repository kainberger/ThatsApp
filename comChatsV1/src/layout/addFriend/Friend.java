package layout.addFriend;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Friend extends HBox {
    Label name = new Label();

    public Friend(String name) {
        this.name.setText(name);

        this.getChildren().add(this.name);
    }
}
