package com.soup.exambyte.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Model for a Test object with a title, description and a list of questions.
 */
public class Test {

  private Integer id;
  private String testTitle;
  private String testDescription;
  private LocalDateTime startTime;
  private LocalDateTime dueTime;
  private final List<Question> questions = new ArrayList<>();

  /**
   * Constructor for a Test object with title and description.

   * @param testTitle title for a Test.
   * @param testDescription description for a Test
   */
  public Test(Integer id, String testTitle, String testDescription) {
    this.id = id;
    this.testTitle = testTitle;
    this.testDescription = testDescription;
  }

  public Test(String testTitle, String testDescription) {
    // TODO: Change id creation
    this.id = (int) (Math.random() * 10);
    this.testTitle = testTitle;
    this.testDescription = testDescription;
  }

  public Test(String testTitle, String testDescription,
      LocalDateTime startTime, LocalDateTime dueTime) {
    // TODO: Change id creation
    this.id = (int) (Math.random() * 10);
    this.testTitle = testTitle;
    this.testDescription = testDescription;
    this.startTime = startTime;
    this.dueTime = dueTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
