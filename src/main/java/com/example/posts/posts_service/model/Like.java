package com.example.posts.posts_service.model;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "post_like")
@EqualsAndHashCode(callSuper = false)
public class Like extends RepresentationModel<Like> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_like")
  private Long id;

  @Column(name = "timestamp")
  private LocalDateTime timestamp;

  @OneToOne
  @JoinColumn(name = "id_author")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "id_post")
  @JsonIgnore
  private Post post;
}
