package com.soup.exambyte.models;

/**
 * Abstract class representing a Question.
 */
public abstract class Question {

  private Integer id;
  private QuestionType questionType;
  private String questionTitle;
  private String questionDescription;

  /**
   * Constructor for Question object with title and description.

   * @param questionTitle       The title of the question.
   * @param questionDescription The description of the question.
   */
  public Question(QuestionType questionType, String questionTitle, String questionDescription) {
    this.id = (int) (Math.random() * 10);
    this.questionType = questionType;
    this.questionTitle = questionTitle;
    this.questionDescription = questionDescription;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}

