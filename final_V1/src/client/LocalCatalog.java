package client;

import muc.Chat;
import muc.Message;
import muc.User;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LocalCatalog {

    private static LocalCatalog instance;
    private List<Serializable> objects;


    private LocalCatalog() {
        this.objects = new LinkedList<>();
    }

    public static LocalCatalog getInstance() {
        if (instance == null) {
            instance = new LocalCatalog();
        }
        return instance;
    }

    public void persist() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("localCatalog.ser"))) {
            oos.writeObject(objects);
        } catch (IOException e) {
            // kann nicht auftreten
            e.printStackTrace();
        }
    }

    public void restore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("localCatalog.ser"))) {
            objects = (List<Serializable>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No Catalog found! Nothing to restore!");
        } catch (InvalidClassException e) {
            System.out.println("Catalog contains class description of different version! Nothing to restore!");
        } catch (ClassNotFoundException | IOException e) {
            // kann nicht auftreten
            e.printStackTrace();
        }
    }

    public List<Message> selectMsgsByChat(Chat chat) {
        List<Message> found = new LinkedList<>();

        for (Serializable s : objects) {
            if (s instanceof Message && ((Message) s).getChat().equals(chat)) {
                found.add((Message) s);
            }
        }

        return found;
    }

    public List<Chat> selectChatsByUser(User user) {
        List<Chat> found = new LinkedList<>();

        for (Serializable s : objects) {
            if (s instanceof Chat && ((Chat) s).getUsers().contains(user)) {
                found.add((Chat) s);
            }
        }

        return found;
    }


    public void add(Serializable ser) {
        if (!objects.contains(ser)) {
            objects.add(ser);
        }
        System.out.println(objects);
    }


    @Override
    public String toString() {
        return "UserCatalog{" +
                "objects=" + objects +
                '}';
    }
}
