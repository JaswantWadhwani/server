package server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import server.dbutil.DBConnection;
import server.model.LoginModel;
import server.model.MessageModel;
import server.model.RegisterModel;
import server.model.UserAccountModel;

public class ServiceUser {

    private final Connection conn;
    private final String INSERT_USER = "insert into users (user_name , password , age) values(?,?,?)";
    private final String CHECK_USER = "select user_id from users where user_name = ?";
    private final String INSERT_USER_ACCOUNT = "insert into user_account (user_id , user_name) values (?,?)";
    private final String GET_USER_ACCOUNT = "select user_id, user_name, gender, image_string, age from user_account where status = '1' and user_id <> ?";
    private final String LOGIN = "select user_id, user_account.user_name, gender, image_string , users.age from `users` join user_account using (User_ID) where `users`.User_Name = BINARY(?) and `users`.`Password`= BINARY(?) and user_account.`Status`='1'";

    public ServiceUser() {
        this.conn = DBConnection.getInstance().getConnection();
    }

    public MessageModel register(RegisterModel data) {

        MessageModel message = new MessageModel();
        try {
            PreparedStatement ps = conn.prepareStatement(CHECK_USER);
            System.out.println("Line 33");
            ps.setString(1, data.getUserName());
            ResultSet rs = ps.executeQu33ery();
            if (rs.next()) {
                System.out.println("Line 36");
                message.setAction(false);
                message.setMessage("User already exist!");
                return message;
            } else {
                message.setAction(true);
            }
            rs.close();
            ps.close();
            if (message.isAction()) {
                
                conn.setAutoCommit(false);
                ps = conn.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, data.getUserName());
                ps.setString(2, data.getPassword());
                ps.setInt(3, data.getAge());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                rs.next();
                int userID = rs.getInt(1);
                rs.close();
                ps.close();
//                Create user_account
                ps = conn.prepareStatement(INSERT_USER_ACCOUNT);
                ps.setInt(1, userID);
                ps.setString(2, data.getUserName());
                ps.execute();
                ps.close();
                conn.commit();
                conn.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("User successfully registered!");
                message.setData(new UserAccountModel(userID, data.getUserName(), "", "", true, data.getAge()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {

            }
        }
        return message;
    }

    public UserAccountModel login(LoginModel login) throws SQLException {
        UserAccountModel data = null;
//        System.out.println("Inside Login");
//        System.out.println(login.getPassword());
        PreparedStatement p = conn.prepareStatement(LOGIN);
//        System.out.println("Line 86");
        p.setString(1, login.getUserName());
//        System.out.println("Line88");
        p.setString(2, login.getPassword());
//        System.out.println("Line90");
        ResultSet r = p.executeQuery();
        if (r.next()) {
//            System.out.println("Line93");
            int userID = r.getInt(1);
//            System.out.println("Line95");
            String userName = r.getString(2);
//            System.out.println("Line97");
            String gender = r.getString(3);
//            System.out.println("Line99");
            String image = r.getString(4);
//            System.out.println("Line101");
            int age = r.getInt(5);
//            System.out.println("Line103");
            data = new UserAccountModel(userID, userName, gender, image, true, age);
//            System.out.println("Line105");
        }
        r.close();
        p.close();
//        System.out.println(data);
        return data;
    }

    public List<UserAccountModel> getUsers(int existingUser) throws SQLException {
        List<UserAccountModel> list = new ArrayList<>();

        PreparedStatement ps = conn.prepareStatement(GET_USER_ACCOUNT);
        ps.setInt(1, existingUser);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            UserAccountModel obj = new UserAccountModel();
            obj.setAge(rs.getInt("age"));
            obj.setGender(rs.getString("gender"));
            obj.setUserId(rs.getInt("user_id"));
            obj.setUserName(rs.getString("user_name"));
            obj.setStatus(true);
            list.add(obj);
        }
        rs.close();
        ps.cancel();
        return list;
    }
}
