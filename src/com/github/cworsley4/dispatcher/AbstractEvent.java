/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cworsley4.dispatcher;

import javax.websocket.Session;

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
    public abstract String getFullyQualifiedEventTitle();
    
    public void setSession(Session s) {
        this.currentSession = s;
    }
    
    public void setData(Object d) {
        this.payloadData = d;
    }
    
    public void setSessions(Session[] ss) {
        this.roomSessions = ss;
    }
    
}
