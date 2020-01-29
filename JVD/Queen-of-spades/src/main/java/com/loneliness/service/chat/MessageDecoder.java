package com.loneliness.service.chat;

import com.loneliness.entity.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public final class MessageDecoder implements Decoder.Text<com.loneliness.entity.Message> {

    @Override
    public void destroy() {
    }

    @Override
    public void init(final EndpointConfig arg0) {
    }

    @Override
    public com.loneliness.entity.Message decode(final String message) throws DecodeException {
        try {
            return Constants.MAPPER.readValue(message, Message.class);
        } catch (IOException e) {
            throw new DecodeException(message, "Unable to decode text to Message", e);
        }
    }

    @Override
    public boolean willDecode(final String arg0) {
        return arg0.contains(Constants.USER_NAME_KEY) && arg0.contains(Constants.MESSAGE_KEY);
    }
}
