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

    public void addUser(User user) throws ThatsAppException {
        if(user == null){
            throw new ThatsAppException("User ist null!");
        }

        if(users.contains(user)){
            throw new ThatsAppException("User ist bereits im Chat!");
        }

        if(!getUsers().contains(user)) {
            getUsers().add(user);
            user.addChat(this);
        }
    }

    public void removeUser(User user) throws ThatsAppException {
        if(getUsers().contains(user)) {
            getUsers().remove(user);
            user.getChats().remove(this);
        }
        else{
            throw new ThatsAppException("User befindet sich nicht im Chat!");
        }
    }

    public User getUserByName(String username) {
        int i = 0;
        while(i < users.size() && !users.get(i).getName().equals(username)){
            i++;
        }

        if(i < users.size()){
            return users.get(i);
        }
        return null;
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


    @Override
    public String toString() {
        return "Chat with " +users.toString();
    }
}
