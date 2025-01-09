package com.soup.exambyte.config;

import com.soup.exambyte.dto.TestForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartTimeBeforeDueTimeValidator implements ConstraintValidator<StartTimeBeforeDueTime, TestForm> {

  @Override
  public boolean isValid(TestForm testForm, ConstraintValidatorContext context) {

    if (testForm.getStartTime() == null || testForm.getDueTime() == null) {
      return true;
    }

    return testForm.getStartTime().isBefore(testForm.getDueTime());
  }
}
