package com.example.posts.posts_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.posts.posts_service.model.Author;
import com.example.posts.posts_service.model.Comment;
import com.example.posts.posts_service.model.Like;
import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.model.Score;
import com.example.posts.posts_service.repository.AuthorRepository;
import com.example.posts.posts_service.repository.CommentRepository;
import com.example.posts.posts_service.repository.LikeRepository;
import com.example.posts.posts_service.repository.PostRepository;
import com.example.posts.posts_service.repository.ScoreRepository;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private LikeRepository likeRepository;

  @Autowired
  private ScoreRepository scoreRepository;

  @Override
  public List<Post> getPosts() {
    return postRepository.findAll();
  }

  @Override
  public Optional<Post> getPostById(Long id) {
    return postRepository.findById(id);
  }

  @Override
  public Post createPost(Post post) {
    if (post == null) {
      throw new IllegalArgumentException("Post object cannot be null");
    }
    if (post.getContent() == null || post.getContent().isEmpty()) {
      throw new IllegalArgumentException("Post content cannot be empty");
    }

    Author author = post.getAuthor();
    Optional<Author> optionalAuthor = authorRepository.findById(author.getId());

    if (optionalAuthor.isPresent()) {
      post.setAuthor(optionalAuthor.get());
      return postRepository.save(post);
    } else {
      throw new IllegalArgumentException("Author not found");
    }

  }

  @Override
  public Post updatePost(Long id, Post post) {
    if (post == null) {
      throw new IllegalArgumentException("Post object cannot be null");
    }
    if (post.getContent() == null || post.getContent().isEmpty()) {
      throw new IllegalArgumentException("Post content cannot be empty");
    }

    Optional<Post> optionalExistingPost = postRepository.findById(id);

    if (optionalExistingPost.isPresent()) {
      Post existingPost = optionalExistingPost.get();
      existingPost.setContent(post.getContent());
      return postRepository.save(existingPost);
    } else {
      throw new IllegalArgumentException("Post not found with ID: " + id);
    }
  }

  @Override
  public void deletePost(Long id) {
    if (!postRepository.existsById(id)) {
      throw new IllegalArgumentException("Post not found with ID: " + id);
    }
    postRepository.deleteById(id);
  }

  @Override
  public Comment addCommentToPost(Long id, Comment comment) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

    Author author = post.getAuthor();
    Optional<Author> optionalAuthor = authorRepository.findById(author.getId());

    if (!optionalAuthor.isPresent()) {
      throw new IllegalArgumentException("Author not found for post with ID: " + id);
    }

    comment.setAuthor(optionalAuthor.get());
    comment.setPost(post);

    return commentRepository.save(comment);
  }

  @Override
  public Like addLikeToPost(Long id, Like like) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

    Author author = post.getAuthor();
    Optional<Author> optionalAuthor = authorRepository.findById(author.getId());

    if (!optionalAuthor.isPresent()) {
      throw new IllegalArgumentException("Author not found for post with ID: " + id);
    }

    like.setAuthor(optionalAuthor.get());
    like.setPost(post);

    return likeRepository.save(like);
  }

  @Override
  public Score addScoreToPost(Long id, Score score) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));

    Author author = post.getAuthor();
    Optional<Author> optionalAuthor = authorRepository.findById(author.getId());

    if (!optionalAuthor.isPresent()) {
      throw new IllegalArgumentException("Author not found for post with ID: " + id);
    }

    if (score == null) {
      throw new IllegalArgumentException("Score object cannot be null");
    }

    double scoreValue = score.getValue();
    if (scoreValue < 0 || scoreValue > 10) {
      throw new IllegalArgumentException("Score value must be between 0 and 10");
    }

    score.setAuthor(optionalAuthor.get());
    score.setPost(post);

    return scoreRepository.save(score);
  }

  @Override
  public List<String> addTagsToPost(Long postId, List<String> tags) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

    if (tags == null || tags.isEmpty()) {
      throw new IllegalArgumentException("Tags list cannot be null or empty");
    }

    post.getTags().addAll(tags);
    postRepository.save(post);

    return post.getTags();
  }

}
