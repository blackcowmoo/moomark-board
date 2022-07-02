package com.moomark.post.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moomark.post.model.entity.Post;

import net.minidev.json.JSONObject;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  @Order(1)
  public void getPostsCountBefore() throws Exception {
    long posts = Long.parseLong(mvc.perform(get("/api/v1/posts/count"))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());

    assertEquals(posts, 0);
  }

  @Test
  @Order(2)
  public void writePost() throws Exception {
    String testTitle = "testTitle";
    String testContent = "testContent";

    JSONObject requestParams = new JSONObject();
    requestParams.put("title", testTitle);
    requestParams.put("content", testContent);

    Post post = mapper.readValue(mvc
        .perform(post("/api/v1/post")
            .header("Content-Type", "application/json").header("x-moom-user-id", "TEST@test")
            .content(requestParams.toJSONString()))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), Post.class);

    assertEquals(post.getTitle(), testTitle);
    assertEquals(post.getContent(), testContent);
    assertEquals(post.getRecommendCount(), 0);
    assertEquals(post.getViewsCount(), 0);
  }

  @Test
  @Order(3)
  public void getPostsCountAfter() throws Exception {
    long posts = Long.parseLong(mvc.perform(get("/api/v1/posts/count"))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());

    assertEquals(posts, 1);
  }

  @Test
  @Order(4)
  public void getPosts() throws Exception {
    Post[] post = mapper.readValue(mvc
        .perform(get("/api/v1/posts").queryParam("limit", "1"))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), Post[].class);

    assertEquals(post.length, 1);
  }

  @Test
  @Order(6)
  public void writePostEmptyTitle() throws Exception {
    String testContent = "testContent";

    JSONObject requestParams = new JSONObject();
    requestParams.put("title", "");
    requestParams.put("content", testContent);

    mvc.perform(post("/api/v1/post")
        .header("Content-Type", "application/json").header("x-moom-user-id", "TEST@test")
        .content(requestParams.toJSONString()))
        .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
  }

  @Test
  @Order(5)
  public void writePostEmptyContent() throws Exception {
    String testTitle = "testTitle";

    JSONObject requestParams = new JSONObject();
    requestParams.put("title", testTitle);
    requestParams.put("content", "");

    mvc.perform(post("/api/v1/post")
        .header("Content-Type", "application/json").header("x-moom-user-id", "TEST@test")
        .content(requestParams.toJSONString()))
        .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
  }

}
