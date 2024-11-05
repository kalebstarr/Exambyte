package com.soup.exambyte.models;


/**
 * QuestionDetails object containing the Title and Description.
 */
public class QuestionDetails {

  private String questionTitle;
  private String questionDescription;

  /**
   * Constructor for a QuestionDetails object with questionTitle and questionDescription.

   * @param questionTitle       The title of the question.
   * @param questionDescription The description of a question.
   */
  public QuestionDetails(String questionTitle, String questionDescription) {
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
