package com.soup.exambyte.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private HttpServletRequest request;

  @Test
  @DisplayName("Error page loads")
  void test_01() throws Exception {
    mockMvc.perform(get("/error")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @DisplayName("Error page has correct title and correct content for error: null")
  void test_02() throws Exception {
    MvcResult result = mockMvc.perform(get("/error")).
        andExpect(model().attribute("title", equalTo("Secret"))).
        andExpect(model().attribute("error", equalTo(null))).
        andReturn();

    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Hey");
    assertThat(html).contains("Nothing to see here =)");
  }
}
