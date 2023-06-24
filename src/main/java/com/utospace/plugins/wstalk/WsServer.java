package com.utospace.plugins.wstalk;

import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.AbstractBuild;
import jenkins.model.Jenkins;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class WsServer implements WebSocketHandler {

    private static final Logger logger = Logger.getLogger(WsServer.class.getName());

    private static int DEFAULT_SERVER_PORT = 9090;

    private static int PING_TIMER_INTERVAL = 20;

    private static WebServer webServer = null;

    private static CopyOnWriteArrayList<WebSocketConnection> connections = new CopyOnWriteArrayList<>();

    private static PingTimerThread pingTimer;
    
    @Initializer(before=InitMilestone.COMPLETED)
    public static void init() {
        WsNotifier.WebSocketDescriptor desc = Jenkins.get().getDescriptorByType(WsNotifier.WebSocketDescriptor.class);
        if(desc != null) {
            logger.info(desc.toString());
            reset(desc.port(), desc.keepalive() ? desc.pingInterval() : -1);
        } else {
            reset(DEFAULT_SERVER_PORT, PING_TIMER_INTERVAL);
        }
    }

    public static void reset(int port, int pingInterval) {
        logger.info("stopping web server");
        if(webServer != null){
            for(WebSocketConnection con : connections){
                con.close();
            }
            connections.clear();
            webServer.stop();
        }

        if(pingTimer != null) pingTimer.terminate();
        logger.info("start websocket server at " + port);

        webServer = WebServers.createWebServer(port) //
            .add("/jenkins", new WsServer());
        webServer.start();

        if (pingInterval > 0) pingTimer = new PingTimerThread(pingInterval);
    }

    public static void send(AbstractBuild<?, ?> build, boolean useStatusFormat) {
        send(build, null, useStatusFormat);
    }

    public static void send(AbstractBuild<?, ?> build, String result, boolean useStatusFormat) {
        try {
            Message message = MessageUtil.makeMessage(build, result);
            send(message);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void send(Message message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(message);
            logger.info(json);

            for(WebSocketConnection con : connections) {
                con.send(json);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }

        // it's not necessary to send out a ping immediately or shortly after having send a client message.
        // reset the ping timer to wait its full interval again after sending
        if (pingTimer != null)
            pingTimer.interrupt();
    }

    protected static void ping() {
    	for (WebSocketConnection con : connections) {
    		con.ping("ping".getBytes());
        }
    }

    public void onOpen(WebSocketConnection connection) {
        connections.add(connection);
    }

    public void onClose(WebSocketConnection connection) {
        connections.remove(connection);
    }

    public void onMessage(WebSocketConnection connection, String message) {
    }

    public void onMessage(WebSocketConnection connection, byte[] message) {
    }

    public void onPing(WebSocketConnection connection, byte[] message) throws Throwable {
    	connection.pong(message);
    }

    public void onPong(WebSocketConnection connection, byte[] message) throws Throwable {
    }

}
