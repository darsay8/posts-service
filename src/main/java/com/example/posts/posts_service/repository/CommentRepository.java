package com.example.posts.posts_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.posts.posts_service.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
