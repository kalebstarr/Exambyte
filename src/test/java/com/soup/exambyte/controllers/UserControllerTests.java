package com.soup.exambyte.controllers;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Index page loads")
  void test_01() throws Exception {
    mockMvc.perform(get("/")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @DisplayName("Index page has correct title")
  void test_02() throws Exception {
    MvcResult result = mockMvc.perform(get("/")).
        andExpect(model().attribute("title", equalTo("Exambyte - Home"))).
        andReturn();

    // TODO: Determine if checking html for contents is necessary
    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Exambyte - Home");
  }

  @Test
  @DisplayName("Test page loads")
  void test_03() throws Exception {
    mockMvc.perform(get("/test/1")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test page has correct title")
  void test_04() throws Exception {
    MvcResult result = mockMvc.perform(get("/test/1")).
        andExpect(model().attribute("title", equalTo("Exambyte - Test"))).
        andReturn();

    // TODO: Determine if checking html for contents is necessary
    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Exambyte - Test");
  }

  @Test
  @DisplayName("Test view returns correct content according to path variables")
  void test_05() throws Exception {
    int testNumber = 5;

    mockMvc.perform(get("/test/{testNumber}",
            testNumber)).
        andDo(print()).
        andExpect(status().isOk());

    // TODO: Once testView returns content update test to check for that returned content
  }

  @Test
  @DisplayName("Question page loads")
  void test_06() throws Exception {
    mockMvc.perform(get("/test/2/question/3")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @DisplayName("Question page has correct title")
  void test_07() throws Exception {
    MvcResult result = mockMvc.perform(get("/test/2/question/3")).
        andExpect(model().attribute("title", equalTo("Exambyte - Question"))).
        andReturn();

    // TODO: Determine if checking html for contents is necessary
    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Exambyte - Question");
  }

  @Test
  @DisplayName("Question returns correct content according to path variables")
  void test_08() throws Exception {
    int testNumber = 5;
    int questionNumber = 8;

    mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
            testNumber,
            questionNumber)).
        andDo(print()).
        andExpect(status().isOk());

    // TODO: Once questionView returns content update test to check for that returned content
  }
}
