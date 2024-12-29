package com.soup.exambyte.models;

/**
 * Abstract class representing a Question.
 */
public abstract class Question {

  private Long id;
  private String questionTitle;
  private String questionDescription;

  /**
   * Constructor for Question object with title and description.

   * @param questionTitle       The title of the question.
   * @param questionDescription The description of the question.
   */
  public Question(String questionTitle, String questionDescription) {
    this.id = (long) (Math.random() * 10);
    this.questionTitle = questionTitle;
    this.questionDescription = questionDescription;
  }

  public String getQuestionTitle() {
    return questionTitle;
  }

  public void setQuestionTitle(String questionTitle) {
    this.questionTitle = questionTitle;
  }

  public String getQuestionDescription() {
    return questionDescription;
  }

  public void setQuestionDescription(String questionDescription) {
    this.questionDescription = questionDescription;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
