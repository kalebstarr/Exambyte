package com.soup.exambyte.controllers;

import com.soup.exambyte.config.OrganizerOnly;
import com.soup.exambyte.dto.TestForm;
import com.soup.exambyte.models.Test;
import com.soup.exambyte.models.TextQuestion;
import com.soup.exambyte.services.QuestionService;
import com.soup.exambyte.services.TestService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
  public String createTestView(Model model) {
    model.addAttribute("title", "Exambyte - Create Test");

    return "create-test";
  }

  @PostMapping("/create-test")
  @OrganizerOnly
  public String createTest(HttpSession session, TestForm testForm) {
    System.out.println(testForm.getTestTitle());

    return "redirect:/admin/create-test";
  }

  @GetMapping("/create-test/{questionNumber}")
  @OrganizerOnly
  public String createQuestionView(Model model) {
    model.addAttribute("title", "Exambyte - Create Question");

    return "create-question";
  }
}
