package server.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTextArea;
import server.model.LoginModel;
import server.model.MessageModel;
import server.model.RegisterModel;
import server.model.UserAccountModel;

public class Service {
    private static Service instance;
    private SocketIOServer server;
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
                    sioc.sendEvent("list_user", list.toArray());
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        });
        server.start();
//        System.out.println(getClass() + " Line 86: Server successfully started");
        textArea.append("Server has started on port : " + PORT_NUMBER + "\n");
    }    
}
