package com.soup.exambyte.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestFormTests {

  @Autowired
  private Validator validator;

  @Test
  @DisplayName("No violations occur with start time before due time")
  void test_01() {
    TestForm testForm = new TestForm();
    testForm.setTestTitle("Test Title");
    testForm.setStartTime(LocalDateTime.now());
    testForm.setDueTime(LocalDateTime.now().plusHours(2));

    Set<ConstraintViolation<TestForm>> violations = validator.validate(testForm);

    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("Violations occur with due time before start time")
  void test_02() {
    TestForm testForm = new TestForm();
    testForm.setTestTitle("Test Title");
    testForm.setStartTime(LocalDateTime.now().plusHours(2));
    testForm.setDueTime(LocalDateTime.now());

    Set<ConstraintViolation<TestForm>> violations = validator.validate(testForm);

    assertThat(violations.size()).isEqualTo(1);
  }
}
