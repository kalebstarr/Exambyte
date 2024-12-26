package com.soup.exambyte.models;

import java.util.ArrayList;
import java.util.List;


/**
 * Model for a Test object with a title, description and a list of questions.
 */
public class Test {

  private Long id;
  private String testTitle;
  // TODO: Determine if descriptions are necessary for Tests and if they should be optional.
  private String testDescription;
  private final List<Question> questions = new ArrayList<>();

  /**
   * Constructor for a Test object with title and description.

   * @param testTitle title for a Test.
   * @param testDescription description for a Test
   */
  public Test(long id, String testTitle, String testDescription) {
    this.id = id;
    this.testTitle = testTitle;
    this.testDescription = testDescription;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  /**
   * Adds a list of questions to a test.

   * @param questions A list of questions.
   */
  public void addQuestions(List<Question> questions) {
    this.questions.addAll(questions);
  }
}
