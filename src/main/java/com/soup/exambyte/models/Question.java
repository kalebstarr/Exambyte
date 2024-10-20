package com.soup.exambyte.models;


/**
 * Model for a question object.
 */
public class Question {

  private String questionTitle;
  private String questionDescription;

  /**
   * Constructor for a Question object with questionTitle and questionDescription.

   * @param questionTitle       The title of the question.
   * @param questionDescription The description of a question.
   */
  public Question(String questionTitle, String questionDescription) {
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
}
