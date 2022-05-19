package test;

import muc.Chat;
import muc.ThatsAppException;
import muc.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ChatTest {
    private Chat chat;
    private LinkedList<User> users;
    private User user;

    public ChatTest() {
    }

    @BeforeEach
    public void setUp() throws ThatsAppException {
        users = new LinkedList<>();
        chat = new Chat(users);
        user = new User("User1");
    }

    @Test
    public void testChat() {
        assertEquals(users, chat.getUsers());
    }

    @Test
    public void testGetUserByName() {
        users.add(user);

        assertEquals(user, chat.getUserByName("User1"));
    }

    @Test
    public void testGetUserByName2() {
        assertNull(chat.getUserByName("User1"));
    }

    @Test
    public void testAddUser() throws ThatsAppException {
        chat.addUser(user);

        assertTrue(chat.getUsers().contains(user));
        assertTrue(user.getChats().contains(chat));
    }

    @Test
    public void testAddUser2() {
        assertThrows(ThatsAppException.class, () -> chat.addUser(new User(null)));
    }

    @Test
    public void testAddUser3() {
        assertThrows(ThatsAppException.class, () -> chat.addUser(null));
    }

    @Test
    public void testRemoveUser() throws ThatsAppException {
        users.add(user);
        chat.removeUser(user);

        assertFalse(chat.getUsers().contains(user));
        assertFalse(user.getChats().contains(chat));
    }

    @Test
    public void testRemoveUser2() {
        assertThrows(ThatsAppException.class, () -> chat.removeUser(user));
    }

    @Test
    public void testRemoveUser3() {
        assertThrows(ThatsAppException.class, () -> chat.removeUser(null));
    }
}
