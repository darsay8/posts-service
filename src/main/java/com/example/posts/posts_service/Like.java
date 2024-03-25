package com.example.posts.posts_service;

public class Like {

  private int id;
  private User user;
  private String timestamp;

  // Constructor
  public Like(int id, User user, String timestamp) {
    this.id = id;
    this.user = user;
    this.timestamp = timestamp;
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getTimestamp() {
    return timestamp;
  }

}
