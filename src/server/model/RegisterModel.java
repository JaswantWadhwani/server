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
public class RegisterModel {
    private String userName;
    private String password;
    private int age;
    public RegisterModel() {
        
    }
    
    public RegisterModel(String userName, String password, int age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getAge() {
        return age;
    }
}
