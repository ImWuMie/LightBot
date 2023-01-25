package qwq.wumie.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import qwq.wumie.Launch;
import qwq.wumie.server.message.GMessage;
import qwq.wumie.utils.GsonUtils;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {
    private int connections = 0;

    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections++;
        Launch.info("一个客户端连接到此,当前连接数: "+connections);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections--;
        Launch.info("一个客户端断开连接,当前连接数: "+connections);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message.startsWith("__Client:") && conn == null) {
            String msg = message.substring("__Client:".length());
            broadcast(msg);
            return;
        }
        GMessage info = GsonUtils.jsonToBean(message,GMessage.class);
        if (info.getPost_type().equalsIgnoreCase("message")) {
            Launch.getMainClient().send(message);
            GMessage.Sender sender = info.getSender();
            Launch.moduleManager.in(info,sender);
            Launch.info("[" + sender.getRole() + "] " + (sender.getCard().isEmpty() ? sender.getNickname() : sender.getCard()) + " -> \n" + info.getRaw_message());
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {
        Launch.info("已开启接收Server.");
    }
}
