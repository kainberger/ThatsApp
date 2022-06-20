package server;

import muc.User;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class UserCatalog {

    private static UserCatalog instance;
    private List<User> objects;


    private UserCatalog() {
        this.objects = new LinkedList<>();
    }

    public static UserCatalog getInstance() {
        if (instance == null) {
            instance = new UserCatalog();
        }
        return instance;
    }

    public void persist() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("catalog.ser"))) {
            oos.writeObject(objects);
        } catch (IOException e) {
            // kann nicht auftreten
            e.printStackTrace();
        }
    }

    public void restore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("catalog.ser"))) {
            objects = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No Catalog found! Nothing to restore!");
        } catch (InvalidClassException e) {
            System.out.println("Catalog contains class description of different version! Nothing to restore!");
        } catch (ClassNotFoundException | IOException e) {
            // kann nicht auftreten
            e.printStackTrace();
        }
    }

    public User getUserbyName(String name) {
        User found = null;

        int i = 0;
        while (i < objects.size() && !objects.get(i).getName().equals(name)) {
            i++;
        }

        if (i < objects.size()) {
            found = objects.get(i);
        }

        return found;
    }

    public boolean addUser(User user) {
        if (!objects.contains(user)) {
            objects.add(user);
            return true;
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return "UserCatalog{" +
                "objects=" + objects +
                '}';
    }
}
