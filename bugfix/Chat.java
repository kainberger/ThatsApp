package muc;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {
    private List<User> users;

    public Chat(List<User> users) {
        setUsers(users);
    }

    public List<User> getUsers() {
        return users;
    }

    private void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Chat))
            return false;

        Chat other = (Chat) o;

        boolean found;
        for (User u : other.getUsers()) {
            found = false;
            for (User x : this.users) {
                if(u.equals(x)) {
                    found = true;
                    break;
                }
            }

            if(!found)
                return false;
        }
        return true;
    }
}
