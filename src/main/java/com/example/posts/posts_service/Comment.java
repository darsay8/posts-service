package com.example.posts.posts_service;

public class Comment {
  private int id;
  private User author;
  private String content;
  private String timestamp;

  // Constructor
  public Comment(int id, User author, String content, String timestamp) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.timestamp = timestamp;
  }

  public int getId() {
    return id;
  }

  public User getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public String getTimestamp() {
    return timestamp;
  }
}