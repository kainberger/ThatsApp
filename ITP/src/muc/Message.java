package muc;

import java.io.Serializable;

public class Message implements Serializable {
    private String msg;
    private User src;
    private User target;


    public Message(String msg, User src, User target) {
        setMsg(msg);
        setSrc(src);
        setTarget(target);
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

    public User getTarget() {
        return target;
    }

    private void setTarget(User target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return msg;
    }
}
