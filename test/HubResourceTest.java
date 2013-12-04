/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import br.hub.model.SubscribeBean;
import br.hub.resource.HubResource;
import java.net.ConnectException;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

/**
 *
 * @author felipe
 */
public class HubResourceTest {
    
    private HubResource hub = new HubResource();
    private LinkedList<String> topics;
    private SubscribeBean subscribeBean = new SubscribeBean();
    private BlockingQueue<SubscribeBean> subscriberQueue;
    
    public HubResourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testPublicarFail() throws ConnectException{
        
        try{
            hub.publicar(" ", " ");
        } catch (NullPointerException e){
            System.out.println("Não é possível publicar tópico, ou, valor inválido");
        }
    }
    
    @Test
    public void testPublicar() throws ConnectException{   
        topics = hub.getTopics();
        topics.add("001");
        hub.setTopics(topics);
        hub.publicar("001", "Úmido");
        assertTrue(hub.isSubscriberNotificado());
    }
    
    @Test
    public void testRegistrar(){
        hub.registrar("002");
        topics = hub.getTopics();
        assertTrue(topics.contains("002"));
    }
    
    @Test
    public void testRegistrarFail(){
        hub.registrar("002");
        hub.registrar("002");
        assertTrue(hub.isContainTopic());
    }
    
    @Test
    public void testSubscribe() throws InterruptedException{
        topics = hub.getTopics();
        topics.add("001");
        hub.setTopics(topics);
        subscribeBean.setTopic("001");
        hub.subscribe(subscribeBean);
        assertTrue(hub.isContainSubscribe());
    }
    
    @Test
    public void testSubscribeFail() throws InterruptedException{
        topics = hub.getTopics();
        topics.add("002");
        hub.setTopics(topics);
        subscribeBean.setTopic("001");
        hub.subscribe(subscribeBean);
        assertTrue(!hub.isContainSubscribe());  
    }
    
    @Test
    public void testUnsubscriber() throws InterruptedException{
        topics = hub.getTopics();
        topics.add("001");
        hub.setTopics(topics);
        subscribeBean.setTopic("001");
        hub.unsubscribe(subscribeBean);
        topics = hub.getTopics();
        topics.add("001");
        hub.setTopics(topics);
        subscribeBean.setTopic("001");
        subscribeBean.setAddress("");
        assertEquals("", subscribeBean.getAddress());
        subscribeBean.setPort(8080);
        assertEquals(8080, subscribeBean.getPort());
        hub.subscribe(subscribeBean);
        assertTrue(hub.isContainSubscribe());       
    }
    
    @Test()
    public void notificar() throws ConnectException{
        subscriberQueue = hub.getSubscriberQueue();
        topics = hub.getTopics();
        topics.add("001");
        hub.setTopics(topics);
        subscribeBean.setTopic("001");
        subscriberQueue.add(subscribeBean);
        hub.publicar("001", "001");
        assertTrue(hub.isSubscriberNotificado());
    }
    
}
    
    