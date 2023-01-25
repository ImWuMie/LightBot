package qwq.wumie.module.impl;

import qwq.wumie.Launch;
import qwq.wumie.module.Module;
import qwq.wumie.server.message.GMessage;
import qwq.wumie.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JRRP extends Module {
    private final List<Info> infoList = new ArrayList<>();

    public JRRP() {
        super("jrrp");
        setEnable(Launch.config.isEnableJRRP());
    }

    @Override
    public void run(String[] args, GMessage message, GMessage.Sender sender) {
        if (message.getRaw_message().equalsIgnoreCase("jrrp reset")) {
            infoList.clear();
            sendMessage("已重置今日人品",message);
            return;
        }
        if (inList(sender.getUser_id(),message.getGroup_id())) {
            sendMessage(
                    "[CQ:at,qq=" + sender.getUser_id() + "]\n" +
                            "您今天已经获取过人品值啦\n" +
                            "您的人品值是: " + get(sender.getUser_id(),message.getGroup_id()).luck
                    , message);
        } else {
            int luck = RandomUtils.nextInt(0, 100);
            sendMessage(
                    "[CQ:at,qq=" + sender.getUser_id() + "]\n" +
                            "您今日的人品值是\n" +
                            luck + "！"
                    , message);
            infoList.add(new Info(sender.getUser_id(),message.getGroup_id(), luck));
        }
    }

    private boolean inList(String qq,String group) {
        for (Info info : infoList) {
            if (info.qq.equalsIgnoreCase(qq) && info.group.equalsIgnoreCase(group)) {
                return true;
            }
        }
        return false;
    }

    private Info get(String qq,String group) {
        for (Info info : infoList) {
            if (info.qq.equalsIgnoreCase(qq) && info.group.equalsIgnoreCase(group)) {
                return info;
            }
        }
        return null;
    }

    record Info(String qq,String group,int luck) {
    }
}
