package com.moomark.post.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moomark.post.model.entity.Post;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
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
  public void getPosts() throws Exception {
    Post[] post = mapper.readValue(mvc
        .perform(get("/api/v1/posts").queryParam("limit", "1"))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), Post[].class);

    assertEquals(post.length, 1);
  }
}
