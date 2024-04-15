package com.example.posts.posts_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.posts.posts_service.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
