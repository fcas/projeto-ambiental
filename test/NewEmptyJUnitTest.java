/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import br.hub.model.SubscribeBean;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author felipe
 */
public class NewEmptyJUnitTest {
    SubscribeBean sb = new SubscribeBean();
    public NewEmptyJUnitTest() {
        
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
    
    @Test
    public void testSubscriber(){
        sb.setPort(11);
        assertEquals(11, sb.getPort());
        sb.setAddress("endereço");
        
        assertEquals("endereço", sb.getAddress());
        sb.setTopic("tópico");
        assertEquals("tópico", sb.getTopic());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}