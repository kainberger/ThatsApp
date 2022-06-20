package muc;

import client.LocalCatalog;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private String name;
    private String passwordHash;
    private String email;
    private final List<Chat> chats;

    public User(String name) throws ThatsAppException {
        setName(name);
        chats = new LinkedList<>();
    }

    public User(String name, String pw, String email, boolean pwIsHash) throws ThatsAppException {
        this(name);
        setEmail(email);
        if(pwIsHash) {
            setPasswordHash(pw);
        }
        else{
            setPassword(pw);
        }
    }

    public String getName() {
        return name;
    }

    private void setName(String name) throws ThatsAppException {
        if(name == null){
            throw new ThatsAppException("Name ist null!");
        }

        if(name.length() < 3){
            throw new ThatsAppException("Name ist zu kurz (< 3)!");
        }

        if(name.length() > 15){
            throw new ThatsAppException("Name ist zu lang (> 15)!");
        }
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPassword(String pw) throws ThatsAppException {
        if(pw.length() < 8){
            throw new ThatsAppException("Passwort muss länger als 8 Zeichen sein!");
        }

        int i = 0;
        while(i < pw.length() && !Character.isDigit(pw.toCharArray()[i])){
            i++;
        }

        if(i >= pw.length()){
            throw new ThatsAppException("Passwort muss mid. eine Ziffer enthalten!");
        }
        i = 0;

        while (i < pw.length() && !Character.isUpperCase(pw.toCharArray()[i])){
            i++;
        }

        if(i >= pw.length()){
            throw new ThatsAppException("Passwort muss Großbuchstaben enthalten!");
        }
        i = 0;

        while(i < pw.length() && !Character.isLowerCase(pw.toCharArray()[i])){
            i++;
        }

        if(i >= pw.length())
            throw new ThatsAppException("Passwort muss Kleinbuchstaben enthalten!");

        setPasswordHash(hashPassword(pw));
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
        LocalCatalog.getInstance().add(chat);
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

    public static String hashPassword (String planePassword) throws ThatsAppException{
        final byte[] salt = " ".getBytes(StandardCharsets.UTF_8);
        final int iterations = 10000;
        final int keyLength = 128;

        try {
            KeySpec spec = new PBEKeySpec(planePassword.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Arrays.toString(hash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex){
            throw new ThatsAppException(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return name ;
    }
}
