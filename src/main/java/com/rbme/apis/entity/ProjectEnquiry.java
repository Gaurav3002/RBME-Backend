package com.rbme.apis.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_enquiries")
public class ProjectEnquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /* =========================================================
       COMPANY / BRAND
    ========================================================= */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    /* =========================================================
       REQUIREMENT
    ========================================================= */

    @Column(nullable = false, length = 100)
    private String category;

    @Column(length = 150)
    private String machine;


    /* =========================================================
       PROJECT
    ========================================================= */

    @Column(name = "project_type", nullable = false, length = 100)
    private String projectType;

    @Column(length = 100)
    private String capacity;

    @Column(length = 100)
    private String product;

    @Column(length = 100)
    private String automation;

    @Column(length = 100)
    private String budget;

    @Column(columnDefinition = "TEXT")
    private String message;


    /* =========================================================
       CUSTOMER DETAILS
    ========================================================= */

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "business_name", length = 200)
    private String businessName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;


    /* =========================================================
       ENQUIRY STATUS
    ========================================================= */

    @Column(nullable = false, length = 30)
    private String status = "NEW";


    /* =========================================================
       TIMESTAMP
    ========================================================= */

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    /* =========================================================
       LIFECYCLE
    ========================================================= */

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null || status.isBlank()) {
            status = "NEW";
        }
    }


    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }


    /* =========================================================
       GETTERS / SETTERS
    ========================================================= */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAutomation() {
        return automation;
    }

    public void setAutomation(String automation) {
        this.automation = automation;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}