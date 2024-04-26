package com.example.posts.posts_service.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "post")
@EqualsAndHashCode(callSuper = false)
public class Post extends RepresentationModel<Post> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_post")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_author")
  private Author author;

  @Column(name = "content")
  private String content;

  @Column(name = "timestamp")
  private LocalDateTime timestamp;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Like> likes;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Score> scores;

  @Column(name = "shares")
  private int shares;

  @ElementCollection
  @Column(name = "media")
  private List<String> media;

  @ElementCollection
  @Column(name = "tags")
  private List<String> tags;

  @Column(name = "location")
  private String location;

  @Column(name = "privacy")
  private String privacy;

  @Column(name = "url")
  private String url;
}