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

   * @return index.html template with custom title attribute.
   */
  @GetMapping("/admin")
  public String admin(Model model) {
    model.addAttribute("title", "Admin");
    return "index";
  }
}
