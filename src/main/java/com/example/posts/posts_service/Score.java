package com.example.posts.posts_service;

public class Score {
  private int id;
  private User user;
  private double score;
  private String timestamp;

  // Constructor
  public Score(int id, User user, double score, String timestamp) {
    this.id = id;
    this.user = user;
    this.score = score;
    this.timestamp = timestamp;
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public double getScore() {
    return score;
  }

  public String getTimestamp() {
    return timestamp;
  }

}
