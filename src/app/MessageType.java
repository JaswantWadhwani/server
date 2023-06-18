/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author Jatin Wadhwani
 */
public enum MessageType {
    TEXT(1), EMOJI(2), FILE(3);
    private final int value;

    public int getValue() {
        return value;
    }

    private MessageType(int value) {
        this.value = value;
    }

    public static MessageType toMessageType(int value) {
        if (value == 1) {
            return TEXT;
        } else if (value == 2) {
            return EMOJI;
        } else {
            return FILE;
        }
    }
}
