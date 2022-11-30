package com.github.wolftein.cryptomines.ext;

import org.java_websocket.framing.Framedata;
import org.web3j.protocol.websocket.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomWebSocketClient extends WebSocketClient {
    public interface  ReconnectHandlerInterface{
        void onReconnect();
    }
    ArrayList<ReconnectHandlerInterface> handlers=new ArrayList<>();

    private static URI parseURI(String serverUrl) {
        try {
            return new URI(serverUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to parse URL: '%s'", serverUrl), e);
        }
    }

    private void doReconnect(){
        if(this.isClosed()){
            try {
                this.reconnectBlocking();
                for (ReconnectHandlerInterface handler : handlers) {
                    handler.onReconnect();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Current thread needs to shutdown, Reconnect to websocket failed");
            }
        }
    }
    public void addReconnectHandler(ReconnectHandlerInterface handler){
        handlers.add(handler);
    }
    public void removeReconnectHandler(ReconnectHandlerInterface handler){
        handlers.remove(handler);
    }
    public CustomWebSocketClient(String serverUri, ReconnectHandlerInterface handler) {
        super(parseURI(serverUri));
        addReconnectHandler(handler);
    }

    public CustomWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void send(String text) throws NotYetConnectedException {
        doReconnect();
        super.send(text);
    }

    @Override
    public void send(byte[] data) throws NotYetConnectedException {
        doReconnect();
        super.send(data);
    }

    @Override
    public void sendPing() throws NotYetConnectedException {
        doReconnect();
        super.sendPing();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if(remote || code!=1000){
            this.doReconnect();

            System.out.println("Reconnecting WebSocket connection to " + uri + ", because of disconnection reason:" + reason);
        }else {
            super.onClose(code, reason, remote);
        }
    }

    @Override
    public void sendFragmentedFrame(Framedata.Opcode op, ByteBuffer buffer, boolean fin) {
        doReconnect();
        super.sendFragmentedFrame(op, buffer, fin);
    }

    @Override
    public void send(ByteBuffer bytes) throws IllegalArgumentException, NotYetConnectedException {
        doReconnect();
        super.send(bytes);
    }

    @Override
    public void sendFrame(Framedata framedata) {
        doReconnect();
        super.sendFrame(framedata);
    }

    @Override
    public void sendFrame(Collection<Framedata> frames) {
        doReconnect();
        super.sendFrame(frames);
    }
}