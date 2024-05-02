package com.example.posts.posts_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.posts.posts_service.model.Author;
import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.repository.AuthorRepository;
import com.example.posts.posts_service.repository.PostRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @InjectMocks
  private PostServiceImpl postService;

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private PostRepository postRepository;

  @Test
  public void testPostServiceCreate() {
    Author author = new Author();
    author.setUsername("Test User");

    when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

    Post post = new Post();
    post.setAuthor(author);
    post.setContent("Test Post Content");

    when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Post savedPost = postService.createPost(post);

    System.out.println("Saved Post: " + savedPost.getId());

    assertEquals("Test Post Content", savedPost.getContent());
    assertEquals(author, savedPost.getAuthor());
  }

  @Test
  public void testPostServiceUpdate() {
    Author author = new Author();
    author.setUsername("Test User");

    when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

    Post post = new Post();
    post.setAuthor(author);
    post.setContent("Test Post Content");

    when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Post savedPost = postService.createPost(post);

    when(postRepository.findById(savedPost.getId())).thenReturn(Optional.of(post));

    Post updatedPost = new Post();
    updatedPost.setAuthor(author);
    updatedPost.setContent("Updated Post Content");

    when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Post savedUpdatedPost = postService.updatePost(savedPost.getId(), updatedPost);

    assertEquals("Updated Post Content", savedUpdatedPost.getContent());
    assertEquals(author, savedUpdatedPost.getAuthor());
  }

  @Test
  public void testPostServiceDelete() {
    Author author = new Author();
    author.setUsername("Test User");

    when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

    Post post = new Post();
    post.setAuthor(author);
    post.setContent("Test Post Content");

    when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Post savedPost = postService.createPost(post);

    when(postRepository.existsById(savedPost.getId())).thenReturn(true);

    postService.deletePost(savedPost.getId());

    verify(postRepository, times(1)).deleteById(savedPost.getId());
  }

  @Test
  public void testGetPostById() throws Exception {
    Author author = new Author();
    author.setUsername("Test User");

    when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

    Post post = new Post();
    post.setContent("Test Post Content");
    post.setAuthor(author);

    when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Post savedPost = postService.createPost(post);

    when(postRepository.existsById(savedPost.getId())).thenReturn(true);
    when(postRepository.findById(savedPost.getId())).thenReturn(Optional.of(savedPost));

    Optional<Post> actualPost = postService.getPostById(savedPost.getId());

    assertEquals(savedPost, actualPost.get());
    assertEquals("Test Post Content", actualPost.get().getContent());
  }

}
