package muc;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private String name;
    private String passwordHash;
    private String email;
    private List<Chat> chats;

    public User(String name) throws ThatsAppException {
        if(name == null){
            throw new ThatsAppException("Name ist null!");
        }

        if(name.length() < 3){
            throw new ThatsAppException("Name ist zu kurz (< 3)!");
        }

        if(name.length() > 15){
            throw new ThatsAppException("Name ist zu lang (> 15)!");
        }

        setName(name);
        chats = new LinkedList<>();
    }

    public User(String name, String pwHash, String email) throws ThatsAppException {
        this(name);
        setPasswordHash(pwHash);
        setEmail(email);

    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ThatsAppException {
        if(email == null){
            throw new ThatsAppException("E-Mail-Adresse ist null!");
        }

        if(email.indexOf('@') == -1){
            throw new ThatsAppException("Ungültige E-Mail-Adresse!");
        }

        if(email.split("@")[1].indexOf('.') == -1){
            throw new ThatsAppException("Ungültige E-Mail-Adresse!");
        }

        if(email.split("@")[1].split("\\.").length > 1) {
            if (email.split("@")[1].split("\\.")[0].length() < 1) {
                throw new ThatsAppException("Ungültige E-Mail-Adresse!");
            }
        }
        else {
            throw new ThatsAppException("Ungültige E-Mail-Adresse!");
            }

        if(email.split("@")[1].split(("\\.")).length > 1 ) {
            if (email.split("@")[1].split(("\\."))[1].length() < 2) {
                throw new ThatsAppException("Ungültige E-Mail-Adresse!");
            }
        }
        else {
            throw new ThatsAppException("Ungültige E-Mail-Adresse!");
        }

        if(email.split("@")[0].length() < 1){
            throw new ThatsAppException("Ungültige E-Mail-Adresse!");
        }

        this.email = email;
    }

    public void addChat(Chat chat) throws ThatsAppException {
        if(chat == null){
            throw new ThatsAppException("Chat ist null!");
        }

        if(chat.getUsers() == null){
            throw new ThatsAppException("Chat hat keine User!");
        }

        chats.add(chat);
        if(!chat.getUsers().contains(this)) {
            chat.addUser(this);
        }
    }

    public void removeChat(Chat chat) throws ThatsAppException {
        if(chat == null){
            throw new ThatsAppException("Chat ist null!");
        }

        if(!chats.contains(chat)){
            throw new ThatsAppException("Chat ist nicht in Kontakten enthalten!");
        }

        chats.remove(chat);
        if(chat.getUsers().contains(this)) {
            chat.removeUser(this);
        }
    }

    public List<Chat> getChats() {
        return chats;
    }

    public Chat getChatByUsers(LinkedList<User> users) {
        int i = 0;

        while (i < chats.size() && !chats.get(i).getUsers().equals(users)){
            i++;
        }

        if(i < chats.size()){
            return chats.get(i);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
