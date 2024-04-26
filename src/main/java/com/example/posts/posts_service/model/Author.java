package com.example.posts.posts_service.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "author")
@EqualsAndHashCode(callSuper = false)
public class Author extends RepresentationModel<Author> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_author")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "profile_picture_url")
  private String profilePictureUrl;
}
