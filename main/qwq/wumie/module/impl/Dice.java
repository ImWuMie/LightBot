package qwq.wumie.module.impl;

import qwq.wumie.Launch;
import qwq.wumie.module.Module;
import qwq.wumie.server.message.GMessage;
import qwq.wumie.utils.RandomUtils;

public class Dice extends Module {
    public Dice() {
        super("骰子");
        setEnable(Launch.config.isEnableDice());
    }

    @Override
    public void run(String[] args, GMessage message, GMessage.Sender sender) {
        int number = RandomUtils.nextInt(1,6);
        sendMessage("你投中了: "+number+" 点!",message);
    }
}
