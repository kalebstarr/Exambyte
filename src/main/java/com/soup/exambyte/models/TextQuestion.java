package com.soup.exambyte.models;


/**
 * Text based variation of a question.
 */
public class TextQuestion extends Question {

  /**
   * Constructor for a TextQuestion object with questionTitle and questionDescription.

   * @param questionTitle       The title of a multiple choice question.
   * @param questionDescription The description of a multiple choice question.
   */
  public TextQuestion(String questionTitle, String questionDescription) {
    super(questionTitle, questionDescription);
  }
}
