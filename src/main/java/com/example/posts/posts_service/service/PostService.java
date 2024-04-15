package com.example.posts.posts_service.service;

import java.util.List;
import java.util.Optional;

import com.example.posts.posts_service.model.Post;
import com.example.posts.posts_service.model.Comment;
import com.example.posts.posts_service.model.Like;
import com.example.posts.posts_service.model.Score;

public interface PostService {

  List<Post> getPosts();

  Optional<Post> getPostById(Long id);

  Post createPost(Post post);

  Post updatePost(Long id, Post post);

  void deletePost(Long id);

  Comment addCommentToPost(Long id, Comment comment);

  Like addLikeToPost(Long id, Like like);

  Score addScoreToPost(Long id, Score score);

  List<String> addTagsToPost(Long id, List<String> tags);

}
