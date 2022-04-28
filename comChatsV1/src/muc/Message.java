package muc;

import java.io.Serializable;

public class Message implements Serializable {
    private String msg;
    private Chat chat;
    private User src;


    public Message(String msg, Chat chat, User src) {
        setMsg(msg);
        setChat(chat);
        setSrc(src);
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
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

    @Override
    public String toString() {
        return msg;
    }
}
