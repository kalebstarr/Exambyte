package com.soup.exambyte.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller for admin requests.
 */
@Controller
public class AdminController {

  /**
   * Handles requests for default admin URL ("/admin").

   * @param model The Spring model.
   * @return      Returns the "index" template.
   */
  // TODO: Determine if this mapping is actually needed as it duplicates index() in UserController.
  @GetMapping("/admin")
  public String adminView(Model model) {
    model.addAttribute("title", "Exambyte - Admin");
    return "index";
  }
}
