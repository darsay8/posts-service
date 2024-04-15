package com.example.posts.posts_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.posts.posts_service.model.Author;
import com.example.posts.posts_service.model.Comment;
import com.example.posts.posts_service.model.Like;
import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.model.Score;
import com.example.posts.posts_service.service.PostService;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping("/users")
  public ResponseEntity<List<Author>> getAllAuthors() {
    return postService.getUsers().isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(postService.getUsers());
  }

  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getAllPosts() {
    return postService.getPosts().isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(postService.getPosts());
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    return postService.getPostById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/posts")
  public Post createPost(@RequestBody Post post) {
    return postService.createPost(post);
  }

  @PutMapping("/posts/{id}")
  public Post putMethodName(@PathVariable Long id, @RequestBody Post post) {
    return postService.updatePost(id, post);
  }

  @DeleteMapping("/posts/{id}")
  public void deletePost(@PathVariable Long id) {
    postService.deletePost(id);
  }

  // Add Comments to Post
  @PostMapping("/posts/{id}/comments")
  public Comment createComment(@PathVariable Long id, @RequestBody Comment comment) {
    return postService.addCommentToPost(id, comment);
  }

  // Add Likes to Post
  @PostMapping("/posts/{id}/likes")
  public Like createLike(@PathVariable Long id, @RequestBody Like like) {
    return postService.addLikeToPost(id, like);
  }

  // Add Score to Post
  @PostMapping("/posts/{id}/scores")
  public Score createScore(@PathVariable Long id, @RequestBody Score score) {
    return postService.addScoreToPost(id, score);
  }

  // Add Tags to Post
  @PatchMapping("/posts/{postId}/tags")
  public List<String> addTagsToPost(@PathVariable Long postId, @RequestBody List<String> tags) {
    return postService.addTagsToPost(postId, tags);
  }

  @PostMapping("/users")
  public Author createAuthor(@RequestBody Author author) {
    return postService.createAuthor(author);
  }

  @GetMapping("/**")
  public ResponseEntity<Void> handleInvalidPath() {
    System.out.println("Not found");
    return ResponseEntity.notFound().build();
  }

}
