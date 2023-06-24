//package com.utospace.plugins.wstalk;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import jakarta.websocket.EncodeException;
//import jakarta.websocket.OnClose;
//import jakarta.websocket.OnError;
//import jakarta.websocket.OnMessage;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//
//@ServerEndpoint(value = "/jenkins", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
//public class JenkinsEndpoint {
//
//    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
//
//    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
//
//    @OnOpen
//    public void onOpen(Session session) {
//        sessions.add(session);
//        System.out.println("WebSocket connection opened: " + session.getId());
//    }
//
//    @OnMessage
//    public void onMessage(Message message, Session session) throws IOException, EncodeException {
//        System.out.println("Received message from " + session.getId() + ": " + message);
//        for (Session peer : sessions) {
//            if (!session.getId().equals(peer.getId())) {
//                peer.getBasicRemote().sendObject(message);
//            }
//        }
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        System.out.println("WebSocket connection closed: " + session.getId());
//        sessions.remove(session);
//    }
//
//    @OnError
//    public void onError(Throwable error) {
//        System.err.println("WebSocket error: " + error.getMessage());
//    }
//
//}
