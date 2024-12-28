package com.soup.exambyte.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.soup.exambyte.config.MethodSecurityConfig;
import com.soup.exambyte.config.RolesConfig;
import com.soup.exambyte.config.SecurityConfig;
import com.soup.exambyte.helper.WithMockOAuth2User;
import com.soup.exambyte.models.MultipleChoiceQuestion;
import com.soup.exambyte.models.TextQuestion;
import com.soup.exambyte.services.QuestionService;
import com.soup.exambyte.services.TestService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(OrganizerController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class, RolesConfig.class})
public class OrganizerControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TestService testService;

  @MockBean
  private QuestionService questionService;

  @Test
  @DisplayName("Admin page fails to load without user being authenticated")
  void test_00_5() throws Exception {
    MvcResult result = mockMvc.perform(get("/admin")).
        andExpect(status().is3xxRedirection()).
        andReturn();

    assertThat(result.getResponse().getRedirectedUrl())
        .contains("oauth2/authorization/github");
  }

  @Test
  @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
  @DisplayName("Index page loads")
  void test_01() throws Exception {
    mockMvc.perform(get("/admin")).
        andDo(print()).
        andExpect(status().isOk());
  }

  @Test
  @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
  @DisplayName("Index page has correct title")
  void test_02() throws Exception {
    when(testService.getAllTests()).
        thenReturn(Optional.of(
            List.of(new com.soup.exambyte.models.Test(
                1, "Test 1", "Test Description"
            ))
        ));

    MvcResult result = mockMvc.perform(get("/admin")).
        andExpect(model().attribute("title", equalTo("Exambyte - Admin"))).
        andExpect(model().attributeExists("tests")).
        andReturn();

    String html = result.getResponse().getContentAsString();
    assertThat(html).contains("Exambyte - Admin");
  }

  @Nested
  class CreateTestViewTest {

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_02_5() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test")).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads")
    void test_03() throws Exception {
      mockMvc.perform(get("/admin/create-test")).
          andDo(print()).
          andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page has correct title")
    void test_04() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test")).
          andExpect(model().attribute("title", equalTo("Exambyte - Create Test"))).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Create Test");
    }
  }

  @Nested
  class CreateTestTests {

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_01() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test").
              with(csrf())).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads with add-question request param")
    void test_02() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test").
              with(csrf()).
              param("add-question", "Add Question")).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test/");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads with add-question request param and no preexisting session")
    void test_03() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test").
              with(csrf()).
              param("add-question", "Add Question")).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test) session.getAttribute("currentTest");

      assertThat(currentTest.getQuestions()).isEmpty();
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test/1");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads with add-question request param and preexisting session")
    void test_04() throws Exception {
      com.soup.exambyte.models.Test sampleTest = mock(com.soup.exambyte.models.Test.class);
      when(sampleTest.getQuestions()).thenReturn(List.of(
          new TextQuestion("Text Question", "Text Question"),
          new MultipleChoiceQuestion("MC Question", "MC Question")
      ));
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(post("/admin/create-test").
              with(csrf()).
              param("add-question", "Add Question").
              session(mockHttpSession)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test)
          session.getAttribute("currentTest");

      assertThat(currentTest.getQuestions().size()).isEqualTo(2);
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test/3");
    }
  }

  @Nested
  class AddQuestionViewTests {

    private final int questionNumber = 15;

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_05() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add Question page loads")
    void test_06() throws Exception {
      mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andDo(print()).
          andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add Question page has correct title")
    void test_07() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andExpect(model().attribute("title", equalTo("Exambyte - Create Question"))).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Add Question");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add question page redirects without preexisting session")
    void test_08() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/{questionNumber}",
              1).
              with(csrf())).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();

      assert session != null;
      assertThat(session.getAttribute("currentTest")).isEqualTo(null);
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test");
    }
  }

  @Nested
  class CreateQuestionTests {

  }
}
