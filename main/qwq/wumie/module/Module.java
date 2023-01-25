package qwq.wumie.module;

import qwq.wumie.Launch;
import qwq.wumie.module.results.AtResult;
import qwq.wumie.module.results.MessageResult;
import qwq.wumie.server.Server;
import qwq.wumie.server.message.GMessage;
import qwq.wumie.utils.GsonUtils;

public abstract class Module {
    public String name;
    public boolean enable = true;

    public Server server = Launch.getMainServer();

    public Module(String name) {
        this.name = name;
    }

    public void setEnable(boolean b) {
        this.enable = b;
    }

    public abstract void run(String[] args, GMessage message, GMessage.Sender sender);

    public void sendMessage(String message,GMessage gMessage) {
        MessageResult result = new MessageResult(new MessageResult.Params(gMessage.getGroup_id(),message));
        server.broadcast(result.toJSON());
    }
}
