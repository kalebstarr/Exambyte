package com.soup.exambyte.dto;

import java.time.LocalDateTime;


public class TestForm {

  private String testTitle;
  private String testDescription;
  private LocalDateTime startTime;
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
