package com.example.posts.posts_service;

import java.util.List;

public class Post {
  private int id;
  private User author;
  private String content;
  private List<String> media;
  private String timestamp;
  private List<Like> likes;
  private List<Comment> comments;
  private List<Score> scores;
  private int shares;
  private List<String> tags;
  private String location;
  private String privacy;
  private String url;

  // Constructor
  public Post(int id, User author, String content, List<String> media, String timestamp, List<Like> likes,
      List<Comment> comments, int shares, List<String> tags, String location, String privacy, String url,
      List<Score> scores) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.media = media;
    this.timestamp = timestamp;
    this.likes = likes;
    this.comments = comments;
    this.shares = shares;
    this.tags = tags;
    this.location = location;
    this.privacy = privacy;
    this.url = url;
    this.scores = scores;

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

  public List<String> getMedia() {
    return media;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public List<Like> getLikes() {
    return likes;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public int getShares() {
    return shares;
  }

  public List<String> getTags() {
    return tags;
  }

  public String getLocation() {
    return location;
  }

  public String getPrivacy() {
    return privacy;
  }

  public String getUrl() {
    return url;
  }

  public List<Score> getScores() {
    return scores;
  }

}