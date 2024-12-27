package com.soup.exambyte.controllers;

import com.soup.exambyte.config.OrganizerOnly;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for admin requests.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

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
}
