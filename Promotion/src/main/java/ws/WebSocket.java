package ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@ServerEndpoint("/socket")
public class WebSocket {

    private static Logger logger = LoggerFactory.getLogger(WebSocket.class);
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void open(Session peer) {
        logger.debug("open() : {}", peer);
        peers.add(peer);
    }

    @OnClose
    public void close(Session peer) {
        logger.debug("close() : {}", peer);
        peers.remove(peer);
    }

    public void notifyAllPeers(String message) {
        logger.info("Notifying peers with message: {}", message);
        for (Session peer : peers) {
            sendMessage(message, peer);
        }
    }

    public void sendMessage(String message, Session peer) {
        try {
            peer.getBasicRemote().sendText(message);
            logger.info("Send text to peer: {}", peer.getId());
        } catch (IOException ioe) {
            logger.error("{} # {}", ioe.getMessage(), ioe.getCause());
        }
    }
}