package com.soup.exambyte.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class TestForm {

  @NotNull(message = "Test title cannot be empty")
  private String testTitle;

  private String testDescription;

  @NotNull(message = "Start time cannot be empty")
  private LocalDateTime startTime;

  @NotNull(message = "Due time cannot be empty")
  private LocalDateTime dueTime;

  public String getTestTitle() {
    return testTitle;
  }

  public void setTestTitle(String testTitle) {
    this.testTitle = testTitle;
  }

  public String getTestDescription() {
    return testDescription;
  }

  public void setTestDescription(String testDescription) {
    this.testDescription = testDescription;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getDueTime() {
    return dueTime;
  }

  public void setDueTime(LocalDateTime dueTime) {
    this.dueTime = dueTime;
  }
}
