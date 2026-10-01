package kth.lab1.Model;

public class User {
    private String username;
    private String role; // should be enum for simplisity

    public User(String username, String role){
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
    
}
