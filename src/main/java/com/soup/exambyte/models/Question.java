package com.soup.exambyte.models;

/**
 * Abstract class representing a Question.
 */
public abstract class Question {

  private final QuestionDetails questionDetails;

  /**
   * Constructor for Question object with title and description.

   * @param questionTitle       The title of the question.
   * @param questionDescription The description of the question.
   */
  public Question(String questionTitle, String questionDescription) {
    this.questionDetails = new QuestionDetails(questionTitle, questionDescription);
  }

  /**
   * Constructor for a Question object with QuestionDetails.

   * @param questionDetails QuestionDetail object.
   */
  public Question(QuestionDetails questionDetails) {
    this.questionDetails = questionDetails;
  }

  public String getQuestionTitle() {
    return questionDetails.getQuestionTitle();
  }

  public String getQuestionDescription() {
    return this.questionDetails.getQuestionDescription();
  }

  public QuestionDetails getQuestionDetails() {
    return this.questionDetails;
  }

  // TODO: Determine if setters are necessary.
}
