package qwq.wumie.module;

import qwq.wumie.Launch;
import qwq.wumie.module.impl.*;
import qwq.wumie.module.impl.register.Register;
import qwq.wumie.server.message.GMessage;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    public static final List<Module> modules = new ArrayList<>();

    public void init(Launch main) {
        add(new Say());
        add(new JRRP());
        add(new Test());
        add(new Dice());
        add(new Register());
        add(new Reload());
    }

    public void add(Module module) {
        modules.add(module);
    }

    public void in(GMessage message, GMessage.Sender sender) {
        for (Module module : modules) {
            String text = message.getRaw_message();
            String[] args = text.split(" ");
            if (args[0].equalsIgnoreCase(module.name)) {
                if (module.enable) module.run(args,message,sender);
            }
        }
    }
}
