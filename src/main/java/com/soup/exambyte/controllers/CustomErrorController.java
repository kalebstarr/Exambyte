package com.soup.exambyte.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
  public String errorView(Model model, HttpServletRequest request) {
    model.addAttribute("title", "Error");

    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());

      if(statusCode == HttpStatus.NOT_FOUND.value()) {
        model.addAttribute("error", HttpStatus.NOT_FOUND.value());
      }
      else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.value());
      }
      else {
        model.addAttribute("error", statusCode);
      }
    }

    return "error";
  }
}
