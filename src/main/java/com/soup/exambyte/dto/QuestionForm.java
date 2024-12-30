package com.soup.exambyte.dto;


import java.util.List;

public class QuestionForm {

  private String questionType;
  private String questionTitle;
  private String questionDescription;
  private List<String> options;
  private List<Integer> correctOptions;

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
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

  public List<String> getOptions() {
    return options;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }

  public List<Integer> getCorrectOptions() {
    return correctOptions;
  }

  public void setCorrectOptions(List<Integer> correctOptions) {
    this.correctOptions = correctOptions;
  }
}
