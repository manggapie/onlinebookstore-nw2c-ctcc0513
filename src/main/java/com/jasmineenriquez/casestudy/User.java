
package com.jasmineenriquez.casestudy;
/**
 *
 * @author Jasmine Enriquez
 */
public class User {
    private int userId;
    private String username;
    private String email;
    private String birthday;
    private String password;
    private byte[] profilePicture;

    public User(int userId, String username, String email, String birthday, String password, byte[] profilePicture) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.profilePicture = profilePicture;
    }
    // Getters and setters for the fields
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}