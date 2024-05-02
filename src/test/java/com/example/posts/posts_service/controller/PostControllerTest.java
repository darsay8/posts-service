package com.example.posts.posts_service.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.posts.posts_service.model.Author;
import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostController.class)
public class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PostService postServiceMock;

  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    this.objectMapper = new ObjectMapper();
  }

  @Test
  public void testGetAllPosts() throws Exception {

    Author author = new Author();
    author.setId(1L);
    author.setUsername("Test User");

    Post post = new Post();
    post.setId(1L);
    post.setAuthor(author);
    post.setContent("Test Post Content");

    Post post2 = new Post();
    post.setId(2L);
    post.setAuthor(author);
    post2.setContent("Test Post Content 2");

    List<Post> posts = Arrays.asList(post, post2);
    when(postServiceMock.getPosts()).thenReturn(posts);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/posts"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(2)))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$._embedded.postList[0].content", Matchers.is("Test Post Content")))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$._embedded.postList[1].content", Matchers.is("Test Post Content 2")));
  }

  @Test
  public void testGetPostById() throws Exception {

    Post post = new Post();
    post.setContent("Test Post Content");

    when(postServiceMock.getPostById(1L)).thenReturn(java.util.Optional.of(post));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test Post Content")));
  }

  @Test
  public void testCreatePost() throws Exception {

    Post post = new Post();
    post.setContent("Test Post Content");

    when(postServiceMock.createPost(post)).thenReturn(post);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/api/posts")
            .content(objectMapper.writeValueAsString(post))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test Post Content")));

    verify(postServiceMock, times(1)).createPost(post);

  }

  @Test
  public void testGetPostByIdNotFound() throws Exception {

    when(postServiceMock.getPostById(1L)).thenReturn(java.util.Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
