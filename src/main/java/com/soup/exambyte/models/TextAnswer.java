package com.soup.exambyte.models;

public class TextAnswer extends  Answer {

  private String answer;

  public TextAnswer(Integer id, Question question) {
    super(question);
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
