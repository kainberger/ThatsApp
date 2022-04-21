package muc;

import java.util.Objects;

public class User {
    private static int running_ID = 0;
    private String name;
    private int id;

    public User(String name) {
        setName(name);
        running_ID++;
        id = running_ID;
    }

    public User(String name, int id) {
        setName(name);
        setID(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }
}
