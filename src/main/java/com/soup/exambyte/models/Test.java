package com.soup.exambyte.models;

import java.util.ArrayList;
import java.util.List;


/**
 * Model for a Test object with a title, description and a list of questions.
 */
public class Test {

  private String testTitle;
  // TODO: Determine if descriptions are necessary for Tests and if they should be optional.
  private String testDescription;
  private final List<Question> questions = new ArrayList<>();

  /**
   * Constructor for a Test object with title and description.

   * @param testTitle title for a Test.
   * @param testDescription description for a Test
   */
  public Test(String testTitle, String testDescription) {
    this.testTitle = testTitle;
    this.testDescription = testDescription;
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

  /**
   * Adds a question to a test.

   * @param question The question to add.
   */
  public void addQuestion(Question question) {
    questions.add(question);
  }
}
