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
public class MessageSendingModel {
  
    
    private int fromUserID;
    private int toUserID;
    private int senderAge;
    private int receiverAge;
    private String text;
    
    public MessageSendingModel() {
    }

    public MessageSendingModel(int fromUserID, int toUserID, int senderAge, int receiverAge, String text) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.senderAge = senderAge;
        this.receiverAge = receiverAge;
        this.text = text;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
