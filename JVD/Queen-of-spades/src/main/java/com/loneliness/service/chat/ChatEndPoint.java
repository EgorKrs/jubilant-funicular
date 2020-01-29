package com.loneliness.service.chat;

import com.loneliness.entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;

@ServerEndpoint(value = "/{chat}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public final class ChatEndPoint {
    private Logger logger = LogManager.getLogger();
    @OnOpen
    public void onOpen(@PathParam("chat") final String userName, final Session session) {
        if (Objects.isNull(userName) || userName.isEmpty()) {
            throw new RegistrationFailedException("User name is required");
        } else {
            session.getUserProperties().put(Constants.USER_NAME_KEY, userName);
            if (ChatSessionManager.register(session)) {
                logger.info("Chat opened for " + userName);

                ChatSessionManager.publish(new com.loneliness.entity.Message((String) session.getUserProperties().get(Constants.USER_NAME_KEY), "**joined the chat**"), session);
            } else {
                logger.error("Unable to register, username already exists");
                throw new RegistrationFailedException("Unable to register, username already exists");
            }
        }
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        if (throwable.getClass() == RegistrationFailedException.class) {
            ChatSessionManager.close(session, CloseCodes.VIOLATED_POLICY, throwable.getMessage());
        }
    }

    @OnMessage
    public void onMessage(final com.loneliness.entity.Message message, final Session session) {
        ChatSessionManager.publish(message, session);
    }

    @OnClose
    public void onClose(final Session session) {
        if (ChatSessionManager.remove(session)) {
            logger.info("Chat closed for %s\n" + session.getUserProperties().get(Constants.USER_NAME_KEY));

            ChatSessionManager.publish(new Message((String) session.getUserProperties().get(Constants.USER_NAME_KEY), "***left the chat***"), session);
        }
    }

    private static final class RegistrationFailedException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public RegistrationFailedException(final String message) {
            super(message);
        }
    }
}
