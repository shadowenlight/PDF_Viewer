package com.codex.projeFX.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "User_id")
  private Long userId;

  @Column(name = "Username")
  private String username;
  
  @Column(name = "Password")
  private String password;
  
  @Column(name = "Role")
  private String role;
  
  //Default Constructor
  public UserEntity() {
	  
  }

  // Parameterized Constructor
  public UserEntity(String username, String password, String role) {
    this.username= username;
    this.password = password;
    this.role = role;
  }

  // Set Functions
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public void setRole(String role) {
	  this.role = role;
  }

  // Get functions
  public Long getUserId() {
    return userId;
  }

  public String getUserName() {
    return username;
  }

  public String getPasssword() {
    return password;
  }
  
  public String getRole() {
	return role;
  }
}
