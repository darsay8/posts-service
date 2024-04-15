package com.example.posts.posts_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.posts.posts_service.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
