package com.soup.exambyte.models;

public abstract class Answer {

  private Long id;
  private Question question;

  public Answer(Long id, Question question) {
    this.id = id;
    this.question = question;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
