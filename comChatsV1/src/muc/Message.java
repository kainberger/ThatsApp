package muc;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Message implements Serializable {
    private Chat chat;
    private User src;
    private Type type;
    private LocalDateTime TimeStamp;


    public Message(Chat chat, User src, Type type) {
        setChat(chat);
        setSrc(src);
        setType(type);
        refreshTimeStamp();
    }

    public User getSrc() {
        return src;
    }

    private void setSrc(User src) {
        this.src = src;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getTimeStamp() {
        return TimeStamp;
    }

    public void refreshTimeStamp() {
        this.TimeStamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Message{" +
                "chat=" + chat +
                ", src=" + src +
                '}';
    }

    public String toShortString() {
        return "chat=" + chat +
                ", src=" + src;
    }
}