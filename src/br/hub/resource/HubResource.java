/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hub.resource;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.Resource;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

import br.hub.model.SubscribeBean;
import javax.ws.rs.Consumes;

import org.push.java.processors.NotifySubscribes;


/**
 * REST Web Service
 *
 * @author jorge
 */
@Path("hub")
public class HubResource {

    /**
     * Creates a new instance of HubResource
     */
    public HubResource() {
    }

    @Resource(name = "subscriberQueue")
    public BlockingQueue<SubscribeBean> subscriberQueue = new LinkedBlockingQueue<SubscribeBean>();

    public LinkedList<String> topics = new LinkedList<String>();
    public boolean subscriberNotificado = false;
    public boolean containTopic = false;
    public boolean containSubscribe = false;
    

   
    
    @PUT
    @Path("publish/{idTopic}")
    public void publicar(@PathParam("idTopic") String idTopic, String valueTopic) {

        System.out.println("publicando feed "+valueTopic);
        
        if(topics.contains(idTopic)){
        	new NotifySubscribes(subscriberQueue.iterator(), valueTopic, idTopic).notificar();
                subscriberNotificado = true;
        }
        
    }

    @PUT
    @Path("register")
    public void registrar(String idTopic){
    	System.out.println("Registrando topico");
    	if(!topics.contains(idTopic)){
    		topics.add(idTopic);
    	}else{
    		System.out.println("Topico com esse id jah foi cadastrado");
                containTopic = true; 
    	}
    	System.out.println("Topico: " + idTopic + "cadastrado");
    }
   
    
    @PUT
    @Path("subscribe")
    public void subscribe(SubscribeBean subscribe) throws InterruptedException {
        System.out.println("Received subscribe request");
        System.out.println("topico " + subscribe.getTopic());

        if(topics.contains(subscribe.getTopic())){
        	subscriberQueue.add(subscribe);
                containSubscribe = true;
        }else{
            System.out.println("topico nao existe");
        }
        
        System.out.println(subscriberQueue.size() + " Subescritos");

    }

    @POST
    @Path("/unsubscribe")
    @Consumes("application/xml")
    public void unsubscribe(SubscribeBean subscribe) {
        System.out.println(subscriberQueue.size() + " Subescritos");

        int size = subscriberQueue.size();

        LinkedBlockingQueue<SubscribeBean> auxiliar = new LinkedBlockingQueue<SubscribeBean>();


        try {
           
            for (int a = 0; a < size; a++) {
                SubscribeBean subscriberAux = subscriberQueue.take();
                auxiliar.add(subscriberAux);
            }


            for (int a = 0; a < size; a++) {
                SubscribeBean subscriberAux = auxiliar.take();

                if (!(subscriberAux.getTopic().equals(subscribe.getTopic())) ||
                        !(subscriberAux.getAddress().equals(subscribe.getAddress()))
                        || subscriberAux.getPort() != subscribe.getPort()) {
                    
                    subscriberQueue.add(subscriberAux);
                    
                }
            }


        } catch (InterruptedException e) {
            System.out.println("erro");
        }

        System.out.println(subscriberQueue.size() + " Subescritos");

    }

    public void publicar(int i, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSubscriberQueue(BlockingQueue<SubscribeBean> subscriberQueue) {
        this.subscriberQueue = subscriberQueue;
    }

    public void setTopics(LinkedList<String> topics) {
        this.topics = topics;
    }

    public BlockingQueue<SubscribeBean> getSubscriberQueue() {
        return subscriberQueue;
    }

    public LinkedList<String> getTopics() {
        return topics;
    }

    public boolean isSubscriberNotificado() {
        return subscriberNotificado;
    }

    public boolean isContainTopic() {
        return containTopic;
    }

    public boolean isContainSubscribe() {
        return containSubscribe;
    }
   
}