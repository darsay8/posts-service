package com.example.posts.posts_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.posts.posts_service.model.Post;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;

  @Test
  public void testPostRepositorySave() {
    Post post = new Post();
    post.setContent("Test Post Content");

    Post savedPost = postRepository.save(post);

    assertNotNull(savedPost.getId());
    assertEquals("Test Post Content", savedPost.getContent());
  }

  @Test
  public void testPostRepositoryFindAll() {
    Post post = new Post();
    post.setContent("Test Post Content");

    postRepository.save(post);

    Iterable<Post> posts = postRepository.findAll();

    assertNotNull(posts);
  }

  @Test
  public void testPostRepositoryFindById() {
    Post post = new Post();

    Post savedPost = postRepository.save(post);
    Post foundPost = postRepository.findById(savedPost.getId()).get();

    assertNotNull(foundPost.getId());
  }

  @Test
  public void testPostRepositoryDelete() {
    Post post = new Post();
    Post savedPost = postRepository.save(post);
    Long postId = savedPost.getId();

    postRepository.deleteById(postId);

    assertFalse(postRepository.existsById(postId));
  }

}
