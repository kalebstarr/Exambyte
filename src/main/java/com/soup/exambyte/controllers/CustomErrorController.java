package com.soup.exambyte.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for error requests and error routing for other controllers.
 */
@Controller
public class CustomErrorController implements ErrorController {

  /**
   * Handles requests for errors ("/error")
   * Specifically validates NOT_FOUND and INTERNAL_SERVER_ERROR and redirects error status to view.

   * @param model   The spring model.
   * @param request HttpServletRequest to get HttpStatus of request.
   * @return        Returns the "error" template.
   */
  @RequestMapping("/error")
  public String errorView(Model model, HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      model.addAttribute("title", "Error");
      int statusCode = Integer.parseInt(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        model.addAttribute("error", HttpStatus.NOT_FOUND.value());
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
      } else {
        model.addAttribute("error", statusCode);
      }
    } else {
      model.addAttribute("title", "Secret");
    }

    return "error";
  }
}
