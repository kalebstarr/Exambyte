package com.soup.exambyte.models;

public class TextQuestion extends Question{

  private String answer = "";

  public TextQuestion(String questionTitle, String questionDescription) {
    super(questionTitle, questionDescription);
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
