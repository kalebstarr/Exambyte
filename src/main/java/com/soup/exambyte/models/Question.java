package com.soup.exambyte.models;

public class Question {

  private String questionTitle;
  private String questionDescription;

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
