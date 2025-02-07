package com.hust.seller.entity;

import jakarta.persistence.*;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UserID")
    private int userID;
    @Column(name="Username")
    private String username;
    @Column(name="Password")
    private String password;
    @Column(name="Email")
    private String email;
    @Column(name="Full_name")
    private String full_name;
    @Column(name="Address")
    private String address;
    @Column(name="Phone_number")
    private String phone_number;
    @Column(name="Is_active")
    private boolean is_active;
    @Column(name="Token")
    private String token;
    @Column(name="Token_expire")
    private LocalDateTime token_expire;
    @Column(name="Money")
    private Double money;
    @Column(name = "Image")
    private String image;

    public User(String username, String password, String email, String full_name, String address, String phone_number) {
        this.username = username;
        this.password = password;
        this.email= email;
        this.full_name = full_name;
        this.address = address;
        this.phone_number = phone_number;
    }
    public User(){};

    public int getUserID() {
        return this.userID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.full_name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNumber() {
        return this.phone_number;
    }

    public boolean getActive() {
        return this.is_active;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setActive(boolean active) {
        this.is_active = active;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Roles",  // Bảng nối
            joinColumns = @JoinColumn(name = "UserID"),  // Khóa ngoại trỏ tới bảng User
            inverseJoinColumns = @JoinColumn(name = "RoleID")  // Khóa ngoại trỏ tới bảng Role
    )
    private Set<Role> roles;  // Danh sách các vai trò của người dùng

    // Getters và Setters
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getToken_expire() {
        return token_expire;
    }

    public void setToken_expire(LocalDateTime token_expire) {
        this.token_expire = token_expire;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public List<String> getAddressDetail() {
        List<String> addressDetail = new ArrayList<>();
        String address = this.address;
        String[] rs = address.split("-");
        for (String ad : rs) {
            addressDetail.add(ad); // Thay addLast bằng add
        }
        return addressDetail;
    }

}
