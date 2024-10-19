package com.soup.exambyte.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Index page loads")
  void test_01() throws Exception {
    this.mockMvc.perform(get("/")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @DisplayName("Index page has correct title")
  void test_02() throws Exception {
    this.mockMvc.perform(get("/")).
        andExpect(model().attribute("title", equalTo("Exambyte - Home")));
  }

}
