package kth.lab1.DB;

public class UserDTO {
    private String username;
    private String EncryptedPassword;

    public UserDTO(String username, String EncryptedPassword){
        this.username = username;
        this.EncryptedPassword = EncryptedPassword;
    }
    
    public String getUsername(){return this.username;}
}
