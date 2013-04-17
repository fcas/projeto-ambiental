package org.push.java.processors;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.hub.model.SubscribeBean;


public class NotifySubscribes {

    private Iterator<SubscribeBean> subscribes;
    private String notificacao;
    private String topic;

    public NotifySubscribes(Iterator<SubscribeBean> subscribes, String notificacao, String topic) {
        this.subscribes = subscribes;
        this.notificacao = notificacao;
        this.topic = topic;
    }

    public void notificar() {

 
        while (subscribes.hasNext()) {


            SubscribeBean subscribe = subscribes.next();

            if (subscribe.getTopic().equals(topic)) {
                try {
                    Socket socket = new Socket(subscribe.getAddress(), subscribe.getPort());
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    
                    System.out.println("notificando " + subscribe.getAddress() + ":" + subscribe.getPort());
                    output.writeObject(notificacao);
                    
                } catch (UnknownHostException ex) {
                    Logger.getLogger(NotifySubscribes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NotifySubscribes.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }
    }
}
