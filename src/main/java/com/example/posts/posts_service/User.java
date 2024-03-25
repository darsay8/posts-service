package com.example.posts.posts_service;

public class User {
  private int id;
  private String username;
  private String profilePictureUrl;

  // Constructor
  public User(int id, String username, String profilePictureUrl) {
    this.id = id;
    this.username = username;
    this.profilePictureUrl = profilePictureUrl;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getProfilePictureUrl() {
    return profilePictureUrl;
  }
}
