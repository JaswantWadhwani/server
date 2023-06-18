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
    
    private int fromUserID;
    private String text;
    private int senderAge;
    private int receiverAge;
    private int messageType;

    public MessageReceivingModel() {
        
    }
    
    public MessageReceivingModel(int fromUserID, String text, int senderAge, int receiverAge, int messageType) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.senderAge = senderAge;
        this.receiverAge = receiverAge;
        this.messageType = messageType;
    }

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

    public int getSenderAge() {
        return senderAge;
    }

    public void setSenderAge(int senderAge) {
        this.senderAge = senderAge;
    }

    public int getReceiverAge() {
        return receiverAge;
    }

    public void setReceiverAge(int receiverAge) {
        this.receiverAge = receiverAge;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    
}
