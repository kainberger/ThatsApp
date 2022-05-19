package test;

import muc.Chat;
import muc.Message;
import muc.ThatsAppException;
import muc.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message msg;
    private User user;
    private Chat c;

    public MessageTest() {}

    @BeforeEach
    public void setUp() throws ThatsAppException {
        LinkedList<User> users = new LinkedList<>();
        user = new User("User1");
        users.add(user);
        c = new Chat(users);

        msg = new Message("Guten Tag", c, user);
    }

    @Test
    public void testMessage() {
        assertEquals("Guten Tag", msg.getMsg());
        assertEquals(user, msg.getSrc());
        assertEquals(c, msg.getChat());
    }
}
