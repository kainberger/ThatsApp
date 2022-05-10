package muc;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String name;
    private String passwordHash;
    private String email;

    public User(String name) {
        setName(name);


    }

    public User(String name, String pwHash, String email) {
        setName(name);
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

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }
}
