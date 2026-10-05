package kth.lab1.UI.DTO;

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String role; // "CUSTOMER", "WAREHOUSE", "ADMIN"

    public UserDTO(int id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // JavaBean getters and setters for JSP EL
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
}
