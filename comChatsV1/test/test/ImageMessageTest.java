package test;

import javafx.scene.image.Image;
import muc.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ImageMessageTest {
    private ImageMessage msg;
    private User user;
    private Chat c;
    private Image img;

    public ImageMessageTest() {}

    @BeforeEach
    public void setUp() throws ThatsAppException {
        LinkedList<User> users = new LinkedList<>();
        user = new User("User1");
        users.add(user);
        c = new Chat(users);
        img = new Image(null, false);

        msg = new ImageMessage(img, c, user, Type.STANDARD);
    }

    @Test
    public void testImageMessage() {
        assertEquals(img, msg.getImg());
        assertEquals(user, msg.getSrc());
        assertEquals(c, msg.getChat());
    }
}
