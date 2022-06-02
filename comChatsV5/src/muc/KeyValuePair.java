package muc;

import java.io.Serializable;
import java.util.Objects;

public class KeyValuePair implements Serializable {
    private User user;
    private Message message;

    public KeyValuePair(User user, Message message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyValuePair that = (KeyValuePair) o;
        return Objects.equals(user, that.user) && Objects.equals(message, that.message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "KeyValuePair{" +
                "user=" + user +
                ", message=" + message +
                '}';
    }
}
