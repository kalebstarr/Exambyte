package com.soup.exambyte.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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
  public String index(Model model) {
    model.addAttribute("title", "Exambyte - Home");
    return "index";
  }

  /**
   * Handles requests for tests ("/tests").

   * @param model The Spring model.
   * @return      Returns the "test" template.
   */
  @GetMapping("/test")
  public String test(Model model) {
    model.addAttribute("title", "Exambyte - Test");
    return "test";
  }
}
