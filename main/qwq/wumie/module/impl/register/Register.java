package qwq.wumie.module.impl.register;

import qwq.wumie.Launch;
import qwq.wumie.module.Module;
import qwq.wumie.server.message.GMessage;
import qwq.wumie.utils.FileUtils;
import qwq.wumie.utils.GsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Register extends Module {
    public static File folder = new File(Launch.data_FOLDER,"users");
    public static List<User> users = new ArrayList<>();

    public Register() {
        super("register");
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String[] args, GMessage message, GMessage.Sender sender) {
        User user = new User(message.getUser_id(), User.Prem.Member,100);
        if (inList(user)) {
            sendMessage("[CQ:at,qq="+sender.getUser_id()+"] 你已经注册啦6",message);
        } else {
            users.add(user);
            String jsonString = GsonUtils.beanToJson(user);
            FileUtils.save(folder, user.getUser_id()+".json", jsonString);
            sendMessage("新用户注册"+" [CQ:at,qq="+sender.getUser_id()+"] ",message);
        }
    }

    public static void load() throws IOException {
        if (!folder.exists()) folder.mkdirs();

        Files.list(Launch.data_FOLDER.toPath().resolve("users")).forEach(path -> {
            if (isValidFile(path)) {
                File f = path.toFile();
                String json = Launch.readText(f).get(0);
                User u = GsonUtils.jsonToBean(json,User.class);
                users.add(u);
            }
        });
    }

    private static boolean isValidFile(Path file) {
        String extension = file.toFile().getName().endsWith(".json") ? "json" : "操你妈又乱放文件";
        return  (extension.equals("json"));
    }

    public static boolean inList(User user) {
        for (User u : users) {
            if (u.getUser_id().equalsIgnoreCase(user.user_id)) {
                return true;
            }
        }
        return false;
    }
}
