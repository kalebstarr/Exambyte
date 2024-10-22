package com.soup.exambyte.controllers;

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
    return "question";
  }
}
