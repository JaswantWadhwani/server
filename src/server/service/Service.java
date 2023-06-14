package server.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import server.model.ClientModel;
import server.model.LoginModel;
import server.model.MessageModel;
import server.model.MessageReceivingModel;
import server.model.MessageSendingModel;
import server.model.RegisterModel;
import server.model.UserAccountModel;

public class Service {
    private static Service instance;
    private SocketIOServer server;
    private List<ClientModel> clients;
    private JTextArea textArea;
    private ServiceUser serviceUser;
    private static final int PORT_NUMBER = 9999;
    
    public static Service getInstance(JTextArea textArea) {
        if(instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }
    
    private Service(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        clients = new ArrayList<>();
    }
    
    public void startServer() {
//        System.out.println(getClass() + " Line 37: Trying to start server");
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("One client connected\n");
            }
        });
        server.addEventListener("register", RegisterModel.class , new DataListener<RegisterModel>() {
            @Override
            public void onData(SocketIOClient sioc, RegisterModel t, AckRequest ar) throws Exception {
//                System.out.println(getClass() + " Line 50: Inside register model event listener");
                MessageModel message = serviceUser.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
//                System.out.println(getClass() + " Line 53: Message = " + message.getMessage() + " , action = " + message.isAction() + ", data = " + message.getData());
                if(message.isAction()) {
                    textArea.append("User has Register :" + t.getUserName() + " Pass :" + t.getPassword() + "Age: " + t.getAge() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (UserAccountModel) message.getData());
                    addClient(sioc,(UserAccountModel)message.getData());
                }
            }
        });
        
        server.addEventListener("login", LoginModel.class, new DataListener<LoginModel>() {
            @Override
            public void onData(SocketIOClient sioc, LoginModel t, AckRequest ar) throws Exception {
//                System.out.println(getClass() + " Line 64: Inside login event listener");
                UserAccountModel login = serviceUser.login(t);
//                System.out.println(getClass() + " Line 66");
                if (login != null) {
                    ar.sendAckData(true, login);
//                    System.out.println(getClass() + "Line 75: Inside onData(), active status = " + login.isStatus());
                    addClient(sioc, login);
                    userConnect(login.getUserId());
                } else {
                    ar.sendAckData(false);
                }
            }
        });
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) {
//                System.out.println(getClass() + " Line 64: Inside getUserDetails event listener");
                try {
                    List<UserAccountModel> list = serviceUser.getUsers(userID);
//                    for(UserAccountModel u : list) {
//                        System.out.println(getClass() + "Inside onData() Line 92: " + u.getAge());
//                    }
                    sioc.sendEvent("list_user", list.toArray());
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        });
        
        server.addEventListener("send_to_user", MessageSendingModel.class, new DataListener<MessageSendingModel>() {
            @Override
            public void onData(SocketIOClient sioc, MessageSendingModel t, AckRequest ar) throws Exception {
                sendToClient(t);
            }
        });
        
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if (userID != 0) {
                    //  removed
                    userDisconnect(userID);
                }
            }
        });
        server.start();
//        System.out.println(getClass() + " Line 86: Server successfully started");
        textArea.append("Server has started on port : " + PORT_NUMBER + "\n");
    }    
    
    private void userConnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }

    private void userDisconnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }

    private void addClient(SocketIOClient client, UserAccountModel user) {
        clients.add(new ClientModel(client, user));
    }

    private void sendToClient(MessageSendingModel data) {
        for (ClientModel c : clients) {
            if (c.getUser().getUserId() == data.getToUserID()) {
//                System.out.println("Line 138: "+data.getAge());
                c.getClient().sendEvent("receive_ms", new MessageReceivingModel(data.getFromUserID(), data.getText(), data.getSenderAge(), data.getReceiverAge()));
//                System.out.println("Line 140: fromUserId = " + data.getFromUserID() + " text = " + data.getText() + " sender's age =  " + data.getSenderAge()+ " receiver's age = " + c.getUser().getAge());
//                System.out.println(getClass() + "Line 141: fromUserId = " + data.getFromUserID() + " text = " + data.getText() + " sender's age =  " + data.getSenderAge()+ " receiver's age = " + data.getReceiverAge());
                break;
            }
        }
    }

    public int removeClient(SocketIOClient client) {
        for (ClientModel d : clients) {
            if (d.getClient() == client) {
                clients.remove(d);
                return d.getUser().getUserId();
            }
        }
        return 0;
    }

    public List<ClientModel> getClients() {
        return clients;
    }
}
