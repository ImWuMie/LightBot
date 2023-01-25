package qwq.wumie.module.impl;

import qwq.wumie.Launch;
import qwq.wumie.module.Module;
import qwq.wumie.server.message.GMessage;

public class Say extends Module {
    public Say() {
        super("say");
        setEnable(Launch.config.isEnableSay());
    }

    @Override
    public void run(String[] args, GMessage message, GMessage.Sender sender) {
        sendMessage(message.getRaw_message().substring((args[0]+" ").length()),message);
    }
}
