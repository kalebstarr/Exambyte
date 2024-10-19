package com.soup.exambyte.models;

import java.util.List;

public class MultipleChoiceQuestion extends Question{

  private List<String> options;
  private int correctIndex;

  public MultipleChoiceQuestion(String questionTitle, String questionDescription, int correctIndex) {
    super(questionTitle, questionDescription);
    this.correctIndex = correctIndex;
  }

  public List<String> getOptions() {
    return options;
  }

  public void addOptions(String option) {
    options.add(option);
  }

  public int getCorrectIndex() {
    return correctIndex;
  }

  public void setCorrectIndex(int correctIndex) {
    this.correctIndex = correctIndex;
  }
}
