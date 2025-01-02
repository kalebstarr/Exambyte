package com.soup.exambyte.models;

public abstract class Answer {

  private Integer id;
  private Question question;

  public Answer(Question question) {
    this.id = (int) (Math.random() * 100);
    this.question = question;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
