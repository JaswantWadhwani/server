/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Jatin Wadhwani
 */
public class MessageModel {
    private boolean action;
    private String message;
    private Object data;

    public MessageModel() {
        
    }
    
    public MessageModel(boolean action, String message, Object data) {
        this.action = action;
        this.message = message;
        this.data = data;
    }
    
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
