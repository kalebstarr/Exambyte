package com.soup.exambyte.models;


/**
 * Model for a text question variation of the question object.
 */
public class TextQuestion extends Question {

  private String answer = "";

  /**
   * Constructor for a TextQuestion object with questionTitle and questionDescription.

   * @param questionTitle       The title of a multiple choice question.
   * @param questionDescription The description of a multiple choice question.
   */
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
