package client;

import layout.chat.ChatC;
import muc.Message;

public class ClientRunMsgOnFXThread extends Thread{

    private Message msg;

    public ClientRunMsgOnFXThread(Message msg){
        this.msg = msg;
    }

    @Override
    public void run() {
        super.run();
        if(msg.getChat().getUsers().contains(Client.user) && msg.getChat().equals(ChatC.getController().chat))
            ChatC.getController().showIncomingMsg(msg);
        else {
            LocalCatalog.getInstance().add(msg);
        }
    }
}
