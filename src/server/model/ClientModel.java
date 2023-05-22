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

import com.corundumstudio.socketio.SocketIOClient;

public class ClientModel {

    public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public UserAccountModel getUser() {
        return user;
    }

    public void setUser(UserAccountModel user) {
        this.user = user;
    }

    public ClientModel (SocketIOClient client, UserAccountModel user) {
        this.client = client;
        this.user = user;
    }

    public ClientModel() {
    }

    private SocketIOClient client;
    private UserAccountModel user;
}