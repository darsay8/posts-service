package com.example.posts.posts_service.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.posts.posts_service.model.Author;
import com.example.posts.posts_service.model.Comment;
import com.example.posts.posts_service.model.Like;
import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.model.Score;
import com.example.posts.posts_service.service.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostController {

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);

  @Autowired
  private PostService postService;

  @GetMapping("/users")
  public CollectionModel<EntityModel<Author>> getAllAuthors() {
    List<Author> authors = postService.getUsers();

    if (authors.isEmpty()) {
      logger.info("No authors found.");
      return CollectionModel.empty();
    } else {
      logger.info("Returning {} authors.", authors.size());

      List<EntityModel<Author>> authorResources = authors.stream()
          .map(author -> EntityModel.of(author))
          .collect(Collectors.toList());

      CollectionModel<EntityModel<Author>> resources = CollectionModel.of(authorResources);

      return resources;
    }
  }

  @GetMapping("/posts")
  public CollectionModel<EntityModel<Post>> getAllPosts() {
    List<Post> posts = postService.getPosts();

    if (posts.isEmpty()) {
      logger.info("No posts found.");
      return CollectionModel.empty();
    } else {
      logger.info("Returning {} posts.", posts.size());

      List<EntityModel<Post>> postResources = posts.stream()
          .map(post -> EntityModel.of(post, WebMvcLinkBuilder
              .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(post.getId())).withSelfRel()))
          .collect(Collectors.toList());

      CollectionModel<EntityModel<Post>> resources = CollectionModel.of(postResources,
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts()).withSelfRel());

      return resources;
    }
  }

  @GetMapping("/posts/{id}")
  public EntityModel<Post> getPostById(@PathVariable Long id) {
    Optional<Post> post = postService.getPostById(id);

    if (post.isPresent()) {
      logger.info("Returning post with id {}.", id);
      EntityModel<Post> postEntityModel = EntityModel.of(post.get(),
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withSelfRel(),
          WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPosts()).withRel("posts"));
      return postEntityModel;
    } else {
      logger.warn("Post with id {} not found.", id);
      throw new PostNotFoundException("Post with id " + id + " not found");
    }
  }

  @PostMapping("/posts")
  public EntityModel<Post> createPost(@RequestBody Post post) {
    logger.info("Creating a new post.");
    Post createdPost = postService.createPost(post);
    EntityModel<Post> postEntityModel = EntityModel.of(createdPost,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(createdPost.getId()))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPosts()).withRel("posts"));
    return postEntityModel;
  }

  @PutMapping("/posts/{id}")
  public EntityModel<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
    logger.info("Updating post with id {}.", id);
    Post updatedPost = postService.updatePost(id, post);
    EntityModel<Post> postEntityModel = EntityModel.of(updatedPost,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPosts()).withRel("posts"));
    return postEntityModel;
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    logger.info("Deleting post with id {}.", id);
    postService.deletePost(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/posts/{id}/comments")
  public EntityModel<Comment> createComment(@PathVariable Long id, @RequestBody Comment comment) {
    logger.info("Creating a new comment for post with id {}.", id);
    Comment createdComment = postService.addCommentToPost(id, comment);
    EntityModel<Comment> commentEntityModel = EntityModel.of(createdComment,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withRel("post"));
    return commentEntityModel;
  }

  @PostMapping("/posts/{id}/likes")
  public EntityModel<Like> createLike(@PathVariable Long id, @RequestBody Like like) {
    logger.info("Creating a new like for post with id {}.", id);
    Like createdLike = postService.addLikeToPost(id, like);
    EntityModel<Like> likeEntityModel = EntityModel.of(createdLike,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withRel("post"));
    return likeEntityModel;
  }

  @PostMapping("/posts/{id}/scores")
  public EntityModel<Score> createScore(@PathVariable Long id, @RequestBody Score score) {
    logger.info("Creating a new score for post with id {}.", id);
    Score createdScore = postService.addScoreToPost(id, score);
    EntityModel<Score> scoreEntityModel = EntityModel.of(createdScore,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withRel("post"));
    return scoreEntityModel;
  }

  @PatchMapping("/posts/{id}/tags")
  public EntityModel<List<String>> addTagsToPost(@PathVariable Long id, @RequestBody List<String> tags) {
    logger.info("Adding tags to post with id {}.", id);
    List<String> updatedTags = postService.addTagsToPost(id, tags);

    EntityModel<List<String>> tagsEntityModel = EntityModel.of(updatedTags,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withRel("post"));

    return tagsEntityModel;
  }

  @PostMapping("/users")
  public EntityModel<Author> createUser(@RequestBody Author author) {
    logger.info("Creating a new user.");
    Author createdAuthor = postService.createAuthor(author);

    return EntityModel.of(createdAuthor, WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAuthors()).withRel("authors"));

  }

  @GetMapping("/posts/{id}/scores/avg")
  public EntityModel<Map<String, Double>> getAverageScore(@PathVariable Long id) {
    logger.info("Getting average score for post with id {}.", id);
    Double averageScore = postService.getAverageScore(id);

    Map<String, Double> scoreMap = new HashMap<>();
    scoreMap.put("averageScore", averageScore);

    return EntityModel.of(scoreMap,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPostById(id)).withRel("post"));
  }

  @GetMapping("/**")
  public ResponseEntity<Void> handleInvalidPath() {
    logger.error("Not found");
    return ResponseEntity.notFound().build();
  }

}
