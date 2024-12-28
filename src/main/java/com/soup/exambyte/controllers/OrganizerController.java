package com.soup.exambyte.controllers;

import com.soup.exambyte.config.OrganizerOnly;
import com.soup.exambyte.dto.TestForm;
import com.soup.exambyte.models.Test;
import com.soup.exambyte.models.TextQuestion;
import com.soup.exambyte.services.QuestionService;
import com.soup.exambyte.services.TestService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
   * Handles requests for default admin URL ("/admin/createtest").

   * @param model The Spring model.
   * @return      Returns the "create-test" template.
   */
  @GetMapping("/createtest")
  @OrganizerOnly
  public String createTestView(Model model) {
    model.addAttribute("title", "Exambyte - Create Test");

    return "create-test";
  }

  @GetMapping("/createtest/{questionNumber}")
  @OrganizerOnly
  public String createQuestionView(Model model) {
    model.addAttribute("title", "Exambyte - Create Question");

    return "create-question";
  }
}
