package com.soup.exambyte.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.soup.exambyte.config.MethodSecurityConfig;
import com.soup.exambyte.config.RolesConfig;
import com.soup.exambyte.config.SecurityConfig;
import com.soup.exambyte.helper.WithMockOAuth2User;
import com.soup.exambyte.models.MultipleChoiceQuestion;
import com.soup.exambyte.models.TextQuestion;
import com.soup.exambyte.services.QuestionService;
import com.soup.exambyte.services.TestService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class, RolesConfig.class})
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TestService testService;

  @MockBean
  private QuestionService questionService;

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
    when(testService.getAllTests()).
        thenReturn(Optional.of(
            List.of(new com.soup.exambyte.models.Test(
                1, "Test 1", "Test Description"
            ))
        ));

    MvcResult result = mockMvc.perform(get("/")).
        andExpect(model().attribute("title", equalTo("Exambyte - Home"))).
        andExpect(model().attributeExists("tests")).
        andReturn();

    // TODO: Determine if checking html for contents is necessary
    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Exambyte - Home");
  }

  @Nested
  class TestViewTests {

    private final int testNumber = 2;

    @BeforeEach
    void setupTestViewTests() {
      when(testService.getTestById(testNumber)).
          thenReturn(Optional.of(new com.soup.exambyte.models.Test(3,
              "Test 3",
              "Test 3 Description")));
    }

    @Test
    @DisplayName("Test page fails to load without user being authenticated")
    void test_02_5() throws Exception {
      MvcResult result = mockMvc.perform(get("/test/{testNumber}",
          testNumber)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Test page loads")
    void test_03() throws Exception {
      mockMvc.perform(get("/test/{testNumber}",
              testNumber)).
          andDo(print()).
          andExpect(status().isOk()).
          andExpect(view().name("test"));
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Test page has correct title")
    void test_04() throws Exception {
      MvcResult result = mockMvc.perform(get("/test/{testNumber}",
              testNumber)).
          andExpect(model().attribute("title", equalTo("Exambyte - Test"))).
          andExpect(model().attributeExists("test")).
          andExpect(model().attributeExists("questions")).
          andReturn();

      // TODO: Determine if checking html for contents is necessary
      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Exambyte - Test");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Test view returns correct content according to path variables")
    void test_05() throws Exception {
      mockMvc.perform(get("/test/{testNumber}",
              testNumber)).
          andDo(print()).
          andExpect(status().isOk());

      // TODO: Once testView returns content update test to check for that returned content
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Test view redirects to '/' with nonexistent testNumber")
    void test_05_5() throws Exception {
      mockMvc.perform(get("/test/{testNumber}",
              Integer.MAX_VALUE)).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andExpect(redirectedUrl("/"));
    }
  }

  @Nested
  class QuestionViewTests {

    private final int testNumber = 2;
    private final int questionNumber = 2;

    @BeforeEach
    void setupQuestionViewTests() {
      when(testService.getTestById(testNumber)).
          thenReturn(Optional.of(new com.soup.exambyte.models.Test(3,
              "Test 3",
              "Test 3 Description")));
      when(questionService.getByTestIdAndQuestionId(testNumber, questionNumber - 1)).
          thenReturn(Optional.of(new TextQuestion("Test 1",
              "Test 1 Description")));
    }

    @Test
    @DisplayName("Question page fails to load without user being authenticated")
    void test_05_5() throws Exception {
      MvcResult result = mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
              testNumber,
              questionNumber)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Question page loads")
    void test_06() throws Exception {
      mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
              testNumber,
              questionNumber)).
          andDo(print()).
          andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Question page has correct title")
    void test_07() throws Exception {
      MvcResult result = mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
              testNumber,
              questionNumber)).
          andExpect(model().attribute("title", equalTo("Exambyte - Question"))).
          andExpect(model().attributeExists("test")).
          andExpect(model().attributeExists("question")).
          andReturn();

      // TODO: Determine if checking html for contents is necessary
      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Exambyte - Question");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Question returns correct content according to path variables")
    void test_08() throws Exception {
      MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion("MCQuestion",
          "Description");
      mcQuestion.addOption("Very detailed Option 1");
      mcQuestion.addOption("Very un-detailed Option 2");

      when(questionService.getByTestIdAndQuestionId(testNumber, questionNumber - 1)).
          thenReturn(Optional.of(mcQuestion));

      MvcResult result = mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
              testNumber,
              questionNumber)).
          andDo(print()).
          andExpect(model().attribute("question", mcQuestion)).
          andExpect(status().isOk()).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Very detailed Option 1");
      assertThat(html).contains("Very un-detailed Option 2");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Question redirects to /test/{testNumber} with nonexistent questionNumber")
    void test_09() throws Exception {
      mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
              testNumber,
              Integer.MAX_VALUE)).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andExpect(redirectedUrl("/test/" + testNumber));
    }

    @Test
    @WithMockOAuth2User(login = "TestUser")
    @DisplayName("Question redirects to '/' with nonexistent testNumber")
    void test_10() throws Exception {
      mockMvc.perform(get("/test/{testNumber}/question/{questionNumber}",
              Integer.MAX_VALUE,
              questionNumber)).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andExpect(redirectedUrl("/"));
    }
  }
}
