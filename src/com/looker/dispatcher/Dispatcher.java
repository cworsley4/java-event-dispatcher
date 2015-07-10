/**
 * 
 * @author Cecil Worsley <cecil@looker.com>
 */
package com.looker.dispatcher;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.websocket.Session;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cecil
 */
public class Dispatcher {
    
    public Map<String, List<Class<? extends AbstractEvent>>> eventsCollection = new HashMap<>();
     
    public Dispatcher() {
        
    }
    
    public void broadcast(String ev, Session[] sessions, Object data) {
        try {
            List<Class<? extends AbstractEvent>> eventRunners =  this.eventsCollection.get(ev);
            for(Class<? extends AbstractEvent> eventRunner : eventRunners) {
                AbstractEvent aev = eventRunner.newInstance();
                aev.setSessions(sessions);
                aev.setData(data);
                (new Thread(aev)).start();
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.INFO, null, ex);
        }
    }
    
    public void register(String eventName, Class<? extends AbstractEvent> ev) {
        System.out.println("Registering " + eventName);
        List<Class<? extends AbstractEvent>> abstractEventClassesList;
        abstractEventClassesList = this.eventsCollection.get(eventName);
        if (abstractEventClassesList == null) {
            System.out.println("Registering " + eventName);
            abstractEventClassesList = new ArrayList<Class<? extends AbstractEvent>>();
        }
        
        abstractEventClassesList.add(ev);
        this.eventsCollection.put(eventName, abstractEventClassesList);
    }
    
}
