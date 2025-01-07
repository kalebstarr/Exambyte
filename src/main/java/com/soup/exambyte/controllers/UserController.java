package com.soup.exambyte.controllers;

import com.soup.exambyte.models.MultipleChoiceQuestion;
import com.soup.exambyte.models.Question;
import com.soup.exambyte.models.QuestionType;
import com.soup.exambyte.models.Test;
import com.soup.exambyte.models.TextQuestion;
import com.soup.exambyte.services.QuestionService;
import com.soup.exambyte.services.TestService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Controller for user requests.
 */
@Controller
public class UserController {

  private final TestService testService;

  private final QuestionService questionService;

  public UserController(TestService testService, QuestionService questionService) {
    this.testService = testService;
    this.questionService = questionService;
  }

  /**
   * Handles requests for root URL ("/").

   * @param model The Spring model.
   * @return      Return the "index" template.
   */
  @GetMapping("/")
  public String indexView(Model model) {
    model.addAttribute("title", "Exambyte - Home");

    Optional<List<Test>> tests = testService.getAllTests();
    tests.ifPresent(testList -> model.addAttribute("tests", testList));

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

    Optional<Test> test = testService.getTestById(testNumber);
    if (test.isEmpty()) {
      return "redirect:/";
    }

    List<Question> questions = questionService.getByTestId(testNumber);

    model.addAttribute("test", test.get());
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

    questionNumber -= 1;

    Optional<Test> test = testService.getTestById(testNumber);
    if (test.isEmpty()) {
      return "redirect:/";
    }

    Optional<Question> question = questionService.getByTestIdAndQuestionId(testNumber,
        questionNumber);
    if (question.isEmpty()) {
      return "redirect:/test/" + testNumber;
    }

    if (question.get().getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
      MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question.get();
      model.addAttribute("question", mcQuestion);
    } else {
      TextQuestion textQuestion = (TextQuestion) question.get();
      model.addAttribute("question", textQuestion);
    }

    model.addAttribute("test", test.get());

    return "question";
  }
}
