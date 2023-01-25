package qwq.wumie;

import org.java_websocket.enums.ReadyState;
import qwq.wumie.config.JsonConfig;
import qwq.wumie.module.Manager;
import qwq.wumie.server.Server;
import qwq.wumie.utils.FileUtils;
import qwq.wumie.utils.GsonUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Launch {
    private static Server mainServer;
    private static RedirectionClient mainClient;
    private static Launch instance;
    public static Manager moduleManager;
    public static boolean running = false;
    public static final File FOLDER = new File("LightBot");
    public static File data_FOLDER = new File(Launch.FOLDER,"java_data");
    public static final File configFile = new File(FOLDER, "java_configs.json");
    public static JsonConfig config = new JsonConfig(false, true, "14514", "ws://127.0.0.1:5608/qb", true, false, false, false);

    public static Server getMainServer() {
        return mainServer;
    }

    public static RedirectionClient getMainClient() {
        return mainClient;
    }

    public static Launch getInstance() {
        return instance;
    }

    public static void stop() {
        running = false;
    }

    public static void startClient() throws InterruptedException, URISyntaxException {
        RedirectionClient client = new RedirectionClient(config.getClientUrl());
        mainClient = client;
        info("--正在启动转发服务--");
        info("ServerUrl: " + client.getURI().toString());
        client.connectA();
        while (running) {
            if (config.isAutoReconnect()) {
                if (!mainClient.getReadyState().equals(ReadyState.OPEN)) {
                    if (config.isDebug()) info("转发端正在尝试连接到Py Server");
                    mainClient.reconnect();
                    Thread.sleep(2500);
                }
            }
        }
        info("[Stop] 正在关闭转发端");
        mainClient.close();
        info("[Stop] 正在关闭服务器");
        mainServer.stop(1000);
        saveConfig();
        Runtime.getRuntime().exit(0);
    }

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        if (!FOLDER.exists()) FOLDER.mkdirs();
        if (!data_FOLDER.exists()) data_FOLDER.mkdirs();
        if (!configFile.exists()) saveConfig();
        loadConfig(configFile);
        saveConfig();
        Server server = new Server(Integer.parseInt(config.getServerPort()));
        Thread clientThread = new Thread(() -> {
            try {
                Launch.startClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clientThread.setName("Client ->");
        clientThread.start();
        Thread.currentThread().setName("Server ->");
        info("--正在启动WebSocket服务--");
        info("Port: " + server.getPort());
        server.start();
        instance = new Launch();
        mainServer = server;
        moduleManager = new Manager();
        moduleManager.init(instance);
        running = true;
        BufferedReader sIn = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            String in = sIn.readLine();
            if (in.equals("exit")) {
                mainServer.stop(1000);
                mainClient.close();
                break;
            }
        }
    }

    public static void reload() {
        loadConfig(configFile);
    }

    public static void loadConfig(File jsonFile) {
        String jsonString = readText(jsonFile).get(0);
        JsonConfig json = GsonUtils.jsonToBean(jsonString, JsonConfig.class);
        config = json;
    }

    public static void saveConfig() {
        String jsonString = GsonUtils.beanToJson(config);
        FileUtils.save(FOLDER, "java_configs.json", jsonString);
    }

    public static ArrayList<String> readText(File inputFile) {
        final ArrayList<String> readContent = new ArrayList<>();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
            String str;
            while ((str = in.readLine()) != null) {
                readContent.add(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readContent;
    }

    public static void info(String message) {
        System.out.println("[QQBot] " + message);
    }
}
