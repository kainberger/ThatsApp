package test;

import muc.Chat;
import muc.ThatsAppException;
import muc.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;
    private Chat chat;
    private LinkedList<User> users;

    public UserTest() {
    }

    @BeforeEach
    public void setUp() throws ThatsAppException {
        user = new User("User1");
        users = new LinkedList<>();
        chat = new Chat(users);
    }

    @Test
    public void testUser() {
        assertEquals("User1", user.getName());
    }

    @Test
    public void testUser2() {
        assertThrows(ThatsAppException.class, () -> new User(null), "Name ist null!");
    }

    @Test
    public void testUser3() {
        assertThrows(ThatsAppException.class, () -> new User("Us"), "Name ist zu kurz (< 3)!");
    }

    @Test
    public void testUser4() {
        assertThrows(ThatsAppException.class, () -> new User("UsernameUsernameUser"), "Name ist zu lang (> 15)!");
    }

    @Test
    public void testGetChatByUsers() throws ThatsAppException {
        user.addChat(chat);

        assertEquals(chat, user.getChatByUsers(users));
    }

    @Test
    public void testGetChatByUsers2() {
        assertNull(user.getChatByUsers(users));
    }

    @Test
    public void testAddChat() throws ThatsAppException {
        user.addChat(chat);

        assertTrue(user.getChats().contains(chat));
        assertTrue(chat.getUsers().contains(user));
    }

    @Test
    public void testAddChat2() {
        assertThrows(ThatsAppException.class, () -> user.addChat(new Chat(null)), "Chat hat keine User!");
    }

    @Test
    public void testAddChat3() {
        assertThrows(ThatsAppException.class, () -> user.addChat(null), "Chat ist null!");
    }

    @Test
    public void testRemoveChat() throws ThatsAppException {
        user.addChat(chat);
        user.removeChat(chat);

        assertFalse(user.getChats().contains(chat));
        assertFalse(chat.getUsers().contains(user));
    }

    @Test
    public void testRemoveChat2() {
        assertThrows(ThatsAppException.class, () -> user.removeChat(chat), "Chat ist nicht in Kontakten enthalten!");
    }

    @Test
    public void testRemoveChat3() throws ThatsAppException {
        user.addChat(chat);

        assertThrows(ThatsAppException.class, () -> user.removeChat(null), "Chat ist null!");
    }
}