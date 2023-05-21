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
public class UserAccountModel {
    
    private int userId;
    private String userName;
    private String gender;
    private String image;
    private boolean status;
    private int age;
    
    public UserAccountModel() {
        
    }
    
    public UserAccountModel(int userId, String userName, String gender, String image, boolean status, int age) {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
        this.age = age;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
