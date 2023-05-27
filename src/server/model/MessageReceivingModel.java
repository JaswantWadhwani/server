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
public class MessageReceivingModel {
    
    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageReceivingModel(int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
    }

    public MessageReceivingModel() {
        
    }

    private int fromUserID;
    private String text;

}
