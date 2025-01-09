package com.soup.exambyte.controllers;

import com.soup.exambyte.config.OrganizerOnly;
import com.soup.exambyte.dto.QuestionForm;
import com.soup.exambyte.dto.TestForm;
import com.soup.exambyte.models.MultipleChoiceQuestion;
import com.soup.exambyte.models.Question;
import com.soup.exambyte.models.QuestionType;
import com.soup.exambyte.models.Test;
import com.soup.exambyte.models.TextQuestion;
import com.soup.exambyte.services.QuestionService;
import com.soup.exambyte.services.TestService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller for admin requests.
 */
@Controller
@RequestMapping("/admin")
public class OrganizerController {

  private final TestService testService;

  private final QuestionService questionService;

  public OrganizerController(TestService testService, QuestionService questionService) {
    this.testService = testService;
    this.questionService = questionService;
  }

  /**
   * Handles requests for default admin URL ("/admin").

   * @param model The Spring model.
   * @return      Returns the "index" template.
   */
  // TODO: Determine if this mapping is actually needed as it duplicates index() in UserController.
  @GetMapping("")
  @OrganizerOnly
  public String adminView(Model model) {
    model.addAttribute("title", "Exambyte - Admin");

    Optional<List<Test>> tests = testService.getAllTests();
    tests.ifPresent(testList -> model.addAttribute("tests", testList));

    return "index";
  }

  /**
   * Handles requests for default admin URL ("/admin/create-test").

   * @param model The Spring model.
   * @return      Returns the "create-test" template.
   */
  @GetMapping("/create-test")
  @OrganizerOnly
  public String createTestView(Model model, HttpSession session) {
    model.addAttribute("title", "Exambyte - Create Test");

    if (session.getAttribute("currentTest") != null) {
      Test test = (Test) session.getAttribute("currentTest");
      model.addAttribute("test", test);
      model.addAttribute("questions", test.getQuestions());
    } else {
      Test test = new Test("", "");
      model.addAttribute("test", test);
    }

    return "create-test";
  }

  /**
   * Handles post requests for "/admin/create-test" for the case when a question needs to be added.

   * @param session     Jakarta HttpSession.
   * @param testForm    Basic TestForm including testTitle and testDescription.
   * @param addQuestion Request parameter to identify between question add and test submission Post request.
   * @return            Returns a redirect to "/admin/create-test".
   */
  @PostMapping("/create-test")
  @OrganizerOnly
  public String createTest(HttpSession session, @Valid TestForm testForm, BindingResult bindingResult,
      @RequestParam(value = "add-question", required = false) String addQuestion,
      @RequestParam(value = "submit", required = false) String submit,
      @RequestParam(value = "cancel", required = false) String cancel) {

    if (addQuestion != null) {
      Test test = new Test(testForm.getTestTitle(), testForm.getTestDescription());
      if (session.getAttribute("currentTest") != null) {
        test = (Test) session.getAttribute("currentTest");
        test.setTestTitle(testForm.getTestTitle());
        test.setTestDescription(testForm.getTestDescription());
      }

      session.setAttribute("currentTest", test);

      return "redirect:/admin/create-test/create-question";
    }

    if (submit != null) {
      if (bindingResult.hasErrors()) {
        return "redirect:/admin/create-test";
      }

      Test createTest = new Test(testForm.getTestTitle(), testForm.getTestDescription(),
          testForm.getStartTime(), testForm.getDueTime());

      // TODO: Write test into database
      session.removeAttribute("currentTest");

      return "redirect:/admin";
    }

    if (cancel != null) {
      session.removeAttribute("currentTest");

      return "redirect:/admin";
    }

    return "redirect:/admin/create-test";
  }

  @GetMapping("/create-test/{questionNumber}")
  @OrganizerOnly
  public String viewQuestion(Model model, HttpSession session,
      @PathVariable(value = "questionNumber") int questionNumber) {
    model.addAttribute("title", "Exambyte - View Question");

    // TODO: Add validation if question is in current session
    Test test = (Test) session.getAttribute("currentTest");
    Optional<Question> question = questionService.getById(questionNumber);

    if (question.isEmpty()) {
      return "redirect:/admin/create-test";
    }

    if (question.get().getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
      MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question.get();
      model.addAttribute("question", mcQuestion);
    } else {
      TextQuestion textQuestion = (TextQuestion) question.get();
      model.addAttribute("question", textQuestion);
    }

    return "view-question";
  }

  @GetMapping("/create-test/create-question")
  @OrganizerOnly
  public String createQuestionView(Model model, HttpSession session) {
    model.addAttribute("title", "Exambyte - Create Question");

    if (session.getAttribute("currentTest") == null) {
      return "redirect:/admin/create-test";
    }

    return "create-question";
  }

  @PostMapping("/create-test/create-question")
  @OrganizerOnly
  public String createQuestion(HttpSession session, QuestionForm questionForm) {

    if (session.getAttribute("currentTest") == null) {
      return "redirect:/admin/create-test";
    }

    Test test = (Test) session.getAttribute("currentTest");
    Question question;

    if ("Multiple Choice".equals(questionForm.getQuestionType())) {
      question = new MultipleChoiceQuestion(questionForm.getQuestionTitle(),
          questionForm.getQuestionDescription(),
          questionForm.getOptions(),
          questionForm.getCorrectOptions());
    } else {
      question = new TextQuestion(questionForm.getQuestionTitle(),
          questionForm.getQuestionDescription());
    }

    test.addQuestion(question);
    session.setAttribute("currentTest", test);

    return "redirect:/admin/create-test";
  }
}
