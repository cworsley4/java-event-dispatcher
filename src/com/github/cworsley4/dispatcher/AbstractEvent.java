/**
 * This is an abstract class that defines the elements and hooks for any
 * and all events. It provides a way to emit messages to patrons of the same
 * room.
 * 
 * @author Cecil Worsley <cecilworsley4@gmail.com>
 * @since 2015/07/02
 */
package com.github.cworsley4.dispatcher;

import java.io.IOException;
import javax.websocket.Session;
import org.json.JSONObject;

/**
 *
 * @author cecil
 */
public abstract class AbstractEvent implements Runnable {
    
    protected Object payloadData;
    protected Session currentSession;
    protected Session[] roomSessions;
        
    public abstract String getEvent();
    public abstract String getTopic();
    public abstract void execute(Session s);
    
    public String getFullyQualifiedEventTitle() {
        return this.getTopic() + ":" + this.getEvent();
    }
    
    public void setSession(Session s) {
        this.currentSession = s;
    }
    
    public void setData(Object d) {
        this.payloadData = d;
    }
    
    public void setSessions(Session[] ss) {
        this.roomSessions = ss;
    }
    
    public void emit(Session s, String event, Object data) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("event", event);
        obj.put("data", data);
        s.getBasicRemote().sendText(obj.toString());
    }    
    
    @Override
    public void run() {
        System.out.print("Emitting out to ");
        System.out.print(this.roomSessions.length);
        System.out.print(" clients");
        System.out.print("\n");
        
        for (Session s : this.roomSessions) {
            this.execute(s);
        }
    }
    
}
