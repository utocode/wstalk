//package com.utospace.plugins.wstalk;
//
//import java.io.StringReader;
//
//import javax.json.Json;
//import javax.json.JsonObject;
//
//import jakarta.websocket.DecodeException;
//import jakarta.websocket.Decoder;
//import jakarta.websocket.EndpointConfig;
//
//public class MessageDecoder implements Decoder.Text<Message> {
//
//    @Override
//    public Message decode(final String textMessage) throws DecodeException {
//        Message message = new Message();
//        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
////        message.setContent(jsonObject.getString("message"));
//        return message;
//    }
//
//    @Override
//    public void init(EndpointConfig config) {
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    @Override
//    public boolean willDecode(String s) {
//        return false;
//    }
//
//}
