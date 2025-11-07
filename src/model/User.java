package src.model;

public class User {
    private String username;
    private String password;
    private Role role;
    private Integer linkedStudentId; // for student accounts

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, Role role, Integer linkedStudentId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedStudentId = linkedStudentId;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public Integer getLinkedStudentId() { return linkedStudentId; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return username + "," + password + "," + role + "," + (linkedStudentId != null ? linkedStudentId : "");
    }
}
