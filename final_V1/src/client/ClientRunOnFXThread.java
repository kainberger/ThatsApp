package client;

import layout.chat.ChatC;
import muc.Message;

public class ClientRunOnFXThread extends Thread{

    private Message msg;

    public ClientRunOnFXThread(Message msg){
        this.msg = msg;
    }

    @Override
    public void run() {
        super.run();
        ChatC.getController().showIncomingMsg(msg);
    }
}
