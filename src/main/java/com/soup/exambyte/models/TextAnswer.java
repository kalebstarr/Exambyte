package com.soup.exambyte.models;

public class TextAnswer extends  Answer {

  private String answer;

  public TextAnswer(Long id, Question question) {
    super(id, question);
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
