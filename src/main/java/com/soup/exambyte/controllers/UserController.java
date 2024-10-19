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

   * @return index.html template with custom title attribute.
   */
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("title", "Exambyte - Home");
    return "index";
  }
}
