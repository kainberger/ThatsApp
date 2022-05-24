package test;

import muc.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TextMessageTest {
    private TextMessage msg;
    private User user;
    private Chat c;

    public TextMessageTest() {}

    @BeforeEach
    public void setUp() throws ThatsAppException {
        LinkedList<User> users = new LinkedList<>();
        user = new User("User1");
        users.add(user);
        c = new Chat(users);

        msg = new TextMessage("Guten Tag", c, user, Type.STANDARD);
    }

    @Test
    public void testTextMessage() {
        assertEquals("Guten Tag", msg.getMsg());
        assertEquals(user, msg.getSrc());
        assertEquals(c, msg.getChat());
    }
}
