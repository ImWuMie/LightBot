package qwq.wumie.config;

public class JsonConfig {
    boolean debug;
    boolean autoReconnect;
    String serverPort;
    String clientUrl;
    boolean enableJRRP;
    boolean enableDice;
    boolean enableSay;
    boolean enableTest;

    public JsonConfig(boolean debug, boolean autoReconnect, String serverPort, String clientUrl, boolean enableJRRP, boolean enableDice, boolean enableSay, boolean enableTest) {
        this.debug = debug;
        this.autoReconnect = autoReconnect;
        this.serverPort = serverPort;
        this.clientUrl = clientUrl;
        this.enableJRRP = enableJRRP;
        this.enableDice = enableDice;
        this.enableSay = enableSay;
        this.enableTest = enableTest;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public boolean isEnableJRRP() {
        return enableJRRP;
    }

    public void setEnableJRRP(boolean enableJRRP) {
        this.enableJRRP = enableJRRP;
    }

    public boolean isEnableDice() {
        return enableDice;
    }

    public void setEnableDice(boolean enableDice) {
        this.enableDice = enableDice;
    }

    public boolean isEnableSay() {
        return enableSay;
    }

    public void setEnableSay(boolean enableSay) {
        this.enableSay = enableSay;
    }

    public boolean isEnableTest() {
        return enableTest;
    }

    public void setEnableTest(boolean enableTest) {
        this.enableTest = enableTest;
    }
}
