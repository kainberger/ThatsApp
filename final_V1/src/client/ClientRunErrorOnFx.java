package client;

import layout.login.LoginC;
import layout.register.RegisterC;


public class ClientRunErrorOnFx extends Thread{

    String errorMsg;
    String controller;

    public ClientRunErrorOnFx(String error, String loginOrRegister){
        this.errorMsg = error;
        this.controller = loginOrRegister;
    }

    @Override
    public void run() {
        super.run();

        if (controller.equals("login")) {
            LoginC.getController().showError(errorMsg);

        }

        if(controller.equals("register")){
            RegisterC.getController().showError(errorMsg);

        }

    }
}
