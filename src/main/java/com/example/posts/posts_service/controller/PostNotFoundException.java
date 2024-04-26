package com.example.posts.posts_service.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(String message) {
    super(message);
  }

}
