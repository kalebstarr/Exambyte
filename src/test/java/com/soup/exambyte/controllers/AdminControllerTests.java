package com.soup.exambyte.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.soup.exambyte.config.MethodSecurityConfig;
import com.soup.exambyte.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class AdminControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Index page loads")
  void test_01() throws Exception {
    mockMvc.perform(get("/admin")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @DisplayName("Index page has correct title")
  void test_02() throws Exception {
    MvcResult result = mockMvc.perform(get("/admin")).
        andExpect(model().attribute("title", equalTo("Exambyte - Admin"))).
        andReturn();

    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Exambyte - Admin");
  }

  @Nested
  class CreateTestViewTest {

    @Test
    @DisplayName("createtest page loads")
    void test_03() throws Exception {
      mockMvc.perform(get("/admin/createtest")).
          andDo(print()).
          andExpect(status().isOk());
    }

    @Test
    @DisplayName("createtest page has correct title")
    void test_02() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/createtest")).
          andExpect(model().attribute("title", equalTo("Exambyte - Create Test"))).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Create Test");
    }
  }
}
