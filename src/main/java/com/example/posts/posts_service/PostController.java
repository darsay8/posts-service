package com.example.posts.posts_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
  private final List<Post> posts = new ArrayList<>();

  public PostController() {

    User user1 = new User(1, "John", "http://example.com/profile1.jpg");
    User user2 = new User(2, "Alice", "http://example.com/profile2.jpg");
    User user3 = new User(3, "Bob", "http://example.com/profile3.jpg");
    User user4 = new User(4, "Emily", "http://example.com/profile4.jpg");
    User user5 = new User(5, "Michael", "http://example.com/profile5.jpg");
    User user6 = new User(6, "Sophia", "http://example.com/profile6.jpg");

    Comment comment = new Comment(1, user2, "This is a comment", "2024-03-25T12:00:00Z");
    Comment comment2 = new Comment(2, user3, "This is another comment", "2024-03-25T12:00:00Z");

    Like like = new Like(1, user2, "2024-03-25T12:00:00Z");
    Like like2 = new Like(1, user4, "2024-03-25T12:00:00Z");
    Like like3 = new Like(1, user5, "2024-03-25T12:00:00Z");
    Like like4 = new Like(1, user6, "2024-03-25T12:00:00Z");

    Score score = new Score(1, user1, 4.5, "2024-03-25T12:00:00Z");
    Score score2 = new Score(2, user2, 6.2, "2024-03-25T12:00:00Z");
    Score score3 = new Score(3, user3, 3.6, "2024-03-25T12:00:00Z");
    Score score4 = new Score(4, user4, 5.3, "2024-03-25T12:00:00Z");

    Post post = new Post(1, user1, "This is a post",
        List.of("http://example.com/image.jpg"), "2024-03-25T12:00:00Z",
        List.of(like, like2, like3, like4), List.of(comment, comment2), 0,
        List.of("tag1", "tag2"), "Location",
        "public",
        "http://example.com/post/1", List.of(score, score2));

    Post post2 = new Post(2, user2, "This is another post",
        List.of("http://example.com/image2.jpg"),
        "2021-01-01T12:00:00Z",
        List.of(like, like2, like3, like4), List.of(comment, comment2), 0,
        List.of("tag1", "tag2"), "Location",
        "public",
        "http://example.com/post/2", List.of(score3, score4));

    posts.add(post);
    posts.add(post2);

  }

  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getAllPosts() {
    if (posts.isEmpty()) {
      System.out.println("No posts found");
      return ResponseEntity.noContent().build();

    } else {
      System.out.println("Posts found: " + posts.size());
      return ResponseEntity.ok(posts);
    }
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable int id) {
    for (Post post : posts) {
      if (post.getId() == id) {
        System.out.println("Order id: " + post.getId() + "found");
        return ResponseEntity.ok(post);
      }
    }
    System.out.println("No posts found with id: " + id);
    return ResponseEntity.notFound().build();
  }

  @GetMapping(path = "/posts/{id}/comments")
  public ResponseEntity<List<Comment>> getComments(@PathVariable int id) {
    for (Post post : posts) {
      if (post.getId() == id) {
        System.out.println(post.getComments().size() + " comments found from post id: " + post.getId());
        return ResponseEntity.ok(post.getComments());

      }
    }
    System.out.println("No comments found from post id: " + id);
    return ResponseEntity.notFound().build();
  }

  @GetMapping(path = "/posts/{id}/likes")
  public ResponseEntity<List<Like>> getLikes(@PathVariable int id) {
    for (Post post : posts) {
      if (post.getId() == id) {
        System.out.println(post.getLikes().size() + " likes found from post id: " + post.getId());
        return ResponseEntity.ok(post.getLikes());
      }
    }
    System.out.println("No likes found from post id: " + id);
    return ResponseEntity.notFound().build();

  }

  @GetMapping("/posts/{id}/likes/total")
  public ResponseEntity<Integer> getTotalLikes(@PathVariable int id) {
    for (Post post : posts) {
      if (post.getId() == id) {
        List<Like> likes = post.getLikes();
        int totalLikes = likes.size();
        System.out.println(totalLikes + " likes found from post id: " + post.getId());
        return ResponseEntity.ok(totalLikes);
      }
    }
    System.out.println("No likes found from post id: " + id);
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/posts/{id}/scores")
  public ResponseEntity<List<Score>> getScores(@PathVariable int id) {
    for (Post post : posts) {
      if (post.getId() == id) {
        List<Score> scores = post.getScores();
        System.out.println(scores.size() + " scores found from post id: " + post.getId());
        return ResponseEntity.ok(scores);
      }
    }
    System.out.println("No scores found from post id: " + id);
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/posts/{id}/scores/avg")
  public ResponseEntity<Double> getAverageScore(@PathVariable int id) {
    for (Post post : posts) {
      if (post.getId() == id) {
        List<Score> scores = post.getScores();
        if (scores.isEmpty()) {
          // If no scores are available, return 204 No Content
          return ResponseEntity.noContent().build();
        }
        double totalScores = 0;
        for (Score score : scores) {
          totalScores += score.getScore();
        }
        double averageScores = totalScores / scores.size();
        System.out.println(averageScores + " average scores found from post id: " + post.getId());
        return ResponseEntity.ok(averageScores);
      }
    }
    System.out.println("No scores found from post id: " + id);
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/**")
  public ResponseEntity<Void> handleInvalidPath() {
    System.out.println("Not found");
    return ResponseEntity.notFound().build();
  }

}
