import java.security.Timestamp;

public class Employee {
    private Long id;
    private String fullName;
    private String email;
    private String department;
    private String jobTitle;
    private Boolean active;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    public Employee(String fullName, String email, String department, String jobTitle, Boolean active,
            Timestamp createdAt, Timestamp updatedAt) {
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.jobTitle = jobTitle;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Long getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getDepartment() {
        return department;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public Boolean getActive() {
        return active;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
