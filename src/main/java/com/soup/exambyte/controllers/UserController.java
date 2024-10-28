package com.soup.exambyte.controllers;

import com.soup.exambyte.models.Question;
import com.soup.exambyte.models.Test;
import com.soup.exambyte.service.QuestionService;
import com.soup.exambyte.service.TestService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Controller for user requests.
 */
@Controller
public class UserController {

  /**
   * Handles requests for root URL ("/").

   * @param model The Spring model.
   * @return      Return the "index" template.
   */
  @GetMapping("/")
  public String indexView(Model model) {
    model.addAttribute("title", "Exambyte - Home");

    TestService testService = new TestService();
    List<Test> tests = testService.getAllTests();
    model.addAttribute("tests", tests);

    return "index";
  }

  /**
   * Handles requests for tests ("/tests").

   * @param model The Spring model.
   * @return      Returns the "test" template.
   */
  @GetMapping("/test/{testNumber}")
  public String testView(Model model,
      @PathVariable(value = "testNumber") int testNumber) {
    model.addAttribute("title", "Exambyte - Test");
    model.addAttribute("testNumber", testNumber);

    TestService testService = new TestService();
    Test test = testService.getTestById(testNumber);

    QuestionService questionService = new QuestionService();
    List<Question> questions = questionService.getByTestId(testNumber);

    model.addAttribute("test", test);
    model.addAttribute("questions", questions);

    return "test";
  }

  /**
   * Handles requests for specific questions within a test.

   * @param model The Spring model.
   * @return      Returns the "question" template.
   */
  @GetMapping("/test/{testNumber}/question/{questionNumber}")
  public String questionView(Model model,
      @PathVariable(value = "testNumber") int testNumber,
      @PathVariable(value = "questionNumber") int questionNumber) {
    model.addAttribute("title", "Exambyte - Question");

    TestService testService = new TestService();
    Test test = testService.getTestById(testNumber);

    QuestionService questionService = new QuestionService();
    Question question = questionService.getByTestIdAndQuestionId(testNumber, questionNumber);

    if (question == null) {
      return "redirect:/test/" + testNumber;
    }

    model.addAttribute("test", test);
    model.addAttribute("question", question);

    return "question";
  }
}
