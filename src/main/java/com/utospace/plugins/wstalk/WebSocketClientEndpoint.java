//package com.utospace.plugins.wstalk;
//
//import java.net.URI;
//
//import jakarta.websocket.ClientEndpoint;
//import jakarta.websocket.ContainerProvider;
//import jakarta.websocket.OnMessage;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.WebSocketContainer;
//
//@ClientEndpoint
//public class WebSocketClientEndpoint {
//
//    private Session session;
//
//    public Session connect(URI uri) throws Exception {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        container.connectToServer(this, uri);
//        return session;
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("Received message from server: " + message);
//    }
//
//}
