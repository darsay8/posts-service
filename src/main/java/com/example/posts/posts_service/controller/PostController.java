package com.example.posts.posts_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.posts.posts_service.model.Author;
import com.example.posts.posts_service.model.Comment;
import com.example.posts.posts_service.model.Like;
import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.model.Score;
import com.example.posts.posts_service.service.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);

  @Autowired
  private PostService postService;

  @GetMapping("/users")
  public ResponseEntity<List<Author>> getAllAuthors() {
    List<Author> authors = postService.getUsers();
    if (authors.isEmpty()) {
      logger.info("No authors found.");
      return ResponseEntity.noContent().build();
    } else {
      logger.info("Returning {} authors.", authors.size());
      return ResponseEntity.ok(authors);
    }
  }

  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getAllPosts() {
    List<Post> posts = postService.getPosts();
    if (posts.isEmpty()) {
      logger.info("No posts found.");
      return ResponseEntity.noContent().build();
    } else {
      logger.info("Returning {} posts.", posts.size());
      return ResponseEntity.ok(posts);
    }
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    return postService.getPostById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> {
          logger.warn("Post with id {} not found.", id);
          return ResponseEntity.notFound().build();
        });
  }

  @PostMapping("/posts")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    logger.info("Creating a new post.");
    Post createdPost = postService.createPost(post);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
  }

  @PutMapping("/posts/{id}")
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
    logger.info("Updating post with id {}.", id);
    Post updatedPost = postService.updatePost(id, post);
    return ResponseEntity.ok(updatedPost);
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    logger.info("Deleting post with id {}.", id);
    postService.deletePost(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/posts/{id}/comments")
  public ResponseEntity<Comment> createComment(@PathVariable Long id, @RequestBody Comment comment) {
    logger.info("Creating a new comment for post with id {}.", id);
    Comment createdComment = postService.addCommentToPost(id, comment);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
  }

  @PostMapping("/posts/{id}/likes")
  public ResponseEntity<Like> createLike(@PathVariable Long id, @RequestBody Like like) {
    logger.info("Creating a new like for post with id {}.", id);
    Like createdLike = postService.addLikeToPost(id, like);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdLike);
  }

  @PostMapping("/posts/{id}/scores")
  public ResponseEntity<Score> createScore(@PathVariable Long id, @RequestBody Score score) {
    logger.info("Creating a new score for post with id {}.", id);
    Score createdScore = postService.addScoreToPost(id, score);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdScore);
  }

  @PatchMapping("/posts/{id}/tags")
  public ResponseEntity<List<String>> addTagsToPost(@PathVariable Long id, @RequestBody List<String> tags) {
    logger.info("Adding tags to post with id {}.", id);
    List<String> updatedTags = postService.addTagsToPost(id, tags);
    return ResponseEntity.ok(updatedTags);
  }

  @PostMapping("/users")
  public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
    logger.info("Creating a new author.");
    Author createdAuthor = postService.createAuthor(author);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
  }

  @GetMapping("/posts/{id}/scores/avg")
  public ResponseEntity<Double> getAverageScore(@PathVariable Long id) {
    logger.info("Getting average score for post with id {}.", id);
    Double averageScore = postService.getAverageScore(id);
    return ResponseEntity.ok(averageScore);
  }

  @GetMapping("/**")
  public ResponseEntity<Void> handleInvalidPath() {
    logger.error("Not found");
    return ResponseEntity.notFound().build();
  }

}
