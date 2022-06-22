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
        ChatC.getController().showIncomingMsg(msg);
    }
}
