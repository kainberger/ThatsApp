package muc;

public class TextMessage extends Message{
    private String msg;

    public TextMessage(String msg, Chat chat, User src, Type type) {
        super(chat, src, type);
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
