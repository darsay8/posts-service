package com.example.posts.posts_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_score")
public class Score {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_score")
  private Long id;

  @Column(name = "score")
  private double score;

  @Column(name = "timestamp")
  private LocalDateTime timestamp;

  @ManyToOne
  @JoinColumn(name = "id_author")
  private Author author;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_post")
  private Post post;

}
