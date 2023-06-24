//package com.utospace.plugins.wstalk;
//
//import javax.json.Json;
//
//import jakarta.websocket.EncodeException;
//import jakarta.websocket.Encoder;
//import jakarta.websocket.EndpointConfig;
//
//public class MessageEncoder implements Encoder.Text<Message> {
//
//    @Override
//    public String encode(final Message message) throws EncodeException {
//        return Json.createObjectBuilder() //
//                .add("job", message.getJob()) //
//                .add("number", message.getNumber()) //
//                .add("result", message.getResult()) //
//                .build() //
//                .toString();
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
//}
