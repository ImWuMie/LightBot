package qwq.wumie.module.impl;

import qwq.wumie.Launch;
import qwq.wumie.module.Module;
import qwq.wumie.server.message.GMessage;

public class Test extends Module {
    private String json = "{}";

    public Test() {
        super("test");
        setEnable(Launch.config.isEnableTest());
    }

    @Override
    public void run(String[] args, GMessage message, GMessage.Sender sender) {
        switch (args[1].toLowerCase()) {
            case "set" -> {
                this.json = message.getRaw_message().substring((args[0]+" "+args[1]+" ").length());
                sendMessage("ÉèÖÃÎª: \n"+json,message);
            }
            case "send" -> {
                server.broadcast(json);
            }
        }
    }
}
