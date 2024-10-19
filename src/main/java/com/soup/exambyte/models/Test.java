package com.soup.exambyte.models;

import java.util.ArrayList;
import java.util.List;

public class Test {

  private String testTitle;
  private String testDescription;
  private final List<Question> questions = new ArrayList<>();

  public Test(String title, String description) {
    this.testTitle = title;
    this.testDescription = description;
  }

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

  public List<Question> getQuestions() {
    return questions;
  }
}
