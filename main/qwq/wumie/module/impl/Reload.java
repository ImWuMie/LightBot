package qwq.wumie.module.impl;

import qwq.wumie.Launch;
import qwq.wumie.module.Module;
import qwq.wumie.server.message.GMessage;

import java.io.File;

public class Reload extends Module {
    public Reload() {
        super("reload");
    }

    @Override
    public void run(String[] args, GMessage message, GMessage.Sender sender) {
        switch (args[1]) {
            case "config" -> {
                Launch.reload();
                sendMessage("已重新加载配置.",message);
            }
        }
    }
}
