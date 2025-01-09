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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    @DisplayName("create-test page without session has correct title and attributes")
    void test_04() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test")).
          andExpect(model().attribute("title", equalTo("Exambyte - Create Test"))).
          andExpect(model().attributeExists("test")).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("Create Test");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page with session has correct attributes")
    void test_05() throws Exception {
      com.soup.exambyte.models.Test sampleTest = mock(com.soup.exambyte.models.Test.class);
      when(sampleTest.getQuestions()).thenReturn(List.of(
          new TextQuestion("Text Question", "Text Question"),
          new MultipleChoiceQuestion("MC Question", "MC Question")
      ));
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(get("/admin/create-test").
              session(mockHttpSession)).
          andExpect(model().attributeExists("test")).
          andExpect(model().attributeExists("questions")).
          andReturn();
    }
  }

  @Nested
  class CreateTestTests {

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_01() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/submit").
              with(csrf())).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads with submit request and preexisting session")
    void test_02() throws Exception {
      com.soup.exambyte.models.Test sampleTest = mock(com.soup.exambyte.models.Test.class);
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(post("/admin/create-test/submit").
              with(csrf()).
              param("testTitle", "Test Title").
              param("startTime", String.valueOf(LocalDateTime.now())).
              param("dueTime", String.valueOf(LocalDateTime.now().plusHours(2))).
              session(mockHttpSession)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test)
          session.getAttribute("currentTest");

      assertThat(currentTest).isEqualTo(null);
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page fails with submit request and invalid testForm")
    void test_03() throws Exception {
      com.soup.exambyte.models.Test sampleTest = mock(com.soup.exambyte.models.Test.class);
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(post("/admin/create-test/submit").
              with(csrf()).
              param("testTitle", "").
              param("startTime", "").
              param("dueTime", "").
              session(mockHttpSession)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test");
    }
  }

  @Nested
  class AddQuestionTests {

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_01() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/add-question").
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
      MvcResult result = mockMvc.perform(post("/admin/create-test/add-question").
              with(csrf()).
              param("testTitle", "Test Title").
              param("startTime", String.valueOf(LocalDateTime.now())).
              param("dueTime", String.valueOf(LocalDateTime.now().plusHours(2)))).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test/create-question");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads with add-question request param and no preexisting session")
    void test_03() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/add-question").
              with(csrf()).
              param("testTitle", "Test Title").
              param("startTime", String.valueOf(LocalDateTime.now())).
              param("dueTime", String.valueOf(LocalDateTime.now().plusHours(2)))).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test) session.getAttribute("currentTest");

      assertThat(currentTest.getQuestions()).isEmpty();
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test/create-question");
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

      MvcResult result = mockMvc.perform(post("/admin/create-test/add-question").
              with(csrf()).
              session(mockHttpSession).
              param("testTitle", "Test Title").
              param("startTime", String.valueOf(LocalDateTime.now())).
              param("dueTime", String.valueOf(LocalDateTime.now().plusHours(2)))).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test)
          session.getAttribute("currentTest");

      assertThat(currentTest.getQuestions().size()).isEqualTo(2);
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test/create-question");
    }
  }

  @Nested
  class CancelTestCreationTests {

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_01() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/cancel").
              with(csrf())).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("create-test page loads without request param and with preexisting session")
    void test_02() throws Exception {
      MockHttpSession mockHttpSession = new MockHttpSession();

      MvcResult result = mockMvc.perform(post("/admin/create-test/cancel").
              with(csrf()).
              session(mockHttpSession)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin");
    }
  }

  @Nested
  class ViewQuestionTests {

    private final int questionNumber = 1;

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_01() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("View Question redirects to '/admin/create-test' if no question is found")
    void test_02() throws Exception {
      when(questionService.getById(questionNumber)).thenReturn(Optional.empty());

      MvcResult result = mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("A multiple choice question adds the attribute questionType with contents MCQuestion")
    void test_03() throws Exception {
      MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion("MCQuestion",
          "Extremely precise Description");
      when(questionService.getById(questionNumber)).thenReturn(Optional.of(
          mcQuestion
      ));

      MvcResult result = mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andDo(print()).
          andExpect(model().attribute("title", equalTo("Exambyte - View Question"))).
          andExpect(model().attribute("question", equalTo(mcQuestion))).
          andExpect(status().isOk()).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("<option selected value=\"Multiple Choice\">");
      assertThat(html).contains("MCQuestion");
      assertThat(html).contains("Extremely precise Description");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("A text question adds the attribute questionType with contents TextQuestion")
    void test_04() throws Exception {
      TextQuestion textQuestion = new TextQuestion("TextQuestion",
          "Very accurate Description");
      when(questionService.getById(questionNumber)).thenReturn(Optional.of(
          textQuestion
      ));

      MvcResult result = mockMvc.perform(get("/admin/create-test/{questionNumber}",
              questionNumber)).
          andDo(print()).
          andExpect(model().attribute("title", equalTo("Exambyte - View Question"))).
          andExpect(model().attribute("question", equalTo(textQuestion))).
          andExpect(status().isOk()).
          andReturn();

      String html = result.getResponse().getContentAsString();
      assertThat(html).contains("<option selected value=\"Text\">");
      assertThat(html).contains("TextQuestion");
      assertThat(html).contains("Very accurate Description");
    }
  }

  @Nested
  class AddQuestionViewTests {

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_05() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test/create-question")).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add Question page loads with preexisting session")
    void test_06() throws Exception {
      com.soup.exambyte.models.Test sampleTest = mock(com.soup.exambyte.models.Test.class);
      when(sampleTest.getQuestions()).thenReturn(List.of(
          new TextQuestion("Text Question", "Text Question"),
          new MultipleChoiceQuestion("MC Question", "MC Question")
      ));
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(get("/admin/create-test/create-question").
              session(mockHttpSession)).
          andDo(print()).
          andExpect(model().attribute("title", equalTo("Exambyte - Create Question"))).
          andExpect(status().isOk()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test)
          session.getAttribute("currentTest");

      assertThat(currentTest.getQuestions().size()).isEqualTo(2);
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add question page redirects without preexisting session")
    void test_08() throws Exception {
      MvcResult result = mockMvc.perform(get("/admin/create-test/create-question")).
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

    @Test
    @DisplayName("Admin page fails to load without user being authenticated")
    void test_01() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/create-question").
              with(csrf())).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl())
          .contains("oauth2/authorization/github");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add Question post redirects without preexisting session")
    void test_02() throws Exception {
      MvcResult result = mockMvc.perform(post("/admin/create-test/create-question").
              with(csrf())).
          andDo(print()).
          andExpect(status().is3xxRedirection()).
          andReturn();

      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add question post redirects with preexisting session")
    void test_04() throws Exception {
      com.soup.exambyte.models.Test sampleTest = mock(com.soup.exambyte.models.Test.class);
      when(sampleTest.getQuestions()).thenReturn(List.of(
          new TextQuestion("Text Question", "Text Question"),
          new MultipleChoiceQuestion("MC Question", "MC Question")
      ));
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(post("/admin/create-test/create-question").
              with(csrf()).
              session(mockHttpSession)).
          andExpect(status().is3xxRedirection()).
          andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test)
          session.getAttribute("currentTest");

      assertThat(currentTest.getQuestions().size()).isEqualTo(2);
      assertThat(result.getResponse().getRedirectedUrl()).
          contains("/admin/create-test");
    }

    @Test
    @WithMockOAuth2User(login = "TestUser", roles = "ORGANIZER")
    @DisplayName("Add multiple choice question")
    void test_05() throws Exception {
      com.soup.exambyte.models.Test sampleTest = new com.soup.exambyte.models.Test("Sample Test",
          "Sample Description");
      MockHttpSession mockHttpSession = new MockHttpSession();
      mockHttpSession.setAttribute("currentTest", sampleTest);

      MvcResult result = mockMvc.perform(post("/admin/create-test/create-question").
              with(csrf()).
              session(mockHttpSession).
              param("questionType", "Multiple Choice").
              param("questionTitle", "Sample MC Question").
              param("questionDescription", "Sample Description").
              param("options[0]", "Option 1").
              param("options[1]", "Option 2").
              param("options[2]", "Option 3"))
          .andExpect(status().is3xxRedirection())
          .andReturn();

      HttpSession session = result.getRequest().getSession();
      assert session != null;
      com.soup.exambyte.models.Test currentTest = (com.soup.exambyte.models.Test) session.getAttribute("currentTest");
    
      assertThat(currentTest.getQuestions().size()).isEqualTo(1);
      assertThat(currentTest.getQuestions().getFirst()).isInstanceOf(MultipleChoiceQuestion.class);
      MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) currentTest.getQuestions().getFirst();
      assertThat(mcQuestion.getOptions()).containsExactly("Option 1", "Option 2", "Option 3");
      assertThat(result.getResponse().getRedirectedUrl()).contains("/admin/create-test");
    }
  }
}
