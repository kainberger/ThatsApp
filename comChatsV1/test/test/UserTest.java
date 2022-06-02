package test;

import muc.Chat;
import muc.ThatsAppException;
import muc.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
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
    public void setEmail() throws ThatsAppException {
        user.setEmail("user@htl.at");
        assertEquals("user@htl.at", user.getEmail());
    }

    @Test
    public void setEmail2() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setEmail("userhtl.at"));
    }

    @Test
    public void setEmail3() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setEmail("@htl.at"));
    }

    @Test
    public void setEmail4() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setEmail("user@.at"));
    }

    @Test
    public void setEmail5() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setEmail("user@htlat"));
    }

    @Test
    public void setEmail6() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setEmail("user@htl."));
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

    @Test
    public void setPassword() throws ThatsAppException {
        user.setPassword("Admin12345");
        assertEquals(User.hashPassword("Admin12345"), user.getPasswordHash());
    }

    @Test
    public void setPassword2() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setPassword("admin"), "Passwort muss länger als 8 Zeichen sein!");
    }

    @Test
    public void setPassword3() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setPassword("adminadmin123"), "Passwort muss Großbuchstaben enthalten!");
    }

    @Test
    public void setPassword4() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setPassword("Adminadmin"), "Passwort muss mid. eine Ziffer enthalten!");
    }

    @Test
    public void setPassword5() throws ThatsAppException {
        assertThrows(ThatsAppException.class, () -> user.setPassword("ADMINADMIN"), "Passwort muss Kleinbuchstaben enthalten!");
    }

    @Test
    public void setPassword6() throws ThatsAppException {
        user.setPassword("Adminadmin123");
    }

    @Test
    public void setPasswordHash() throws ThatsAppException {
        user.setPasswordHash(User.hashPassword("Admin12345"));
        assertEquals(User.hashPassword("Admin12345"), user.getPasswordHash());
    }
}