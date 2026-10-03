package kth.lab1.Model.records;
//A user, representative of a row in user table
public record User(
    String username,
    String role,
    String password
) {
} 
