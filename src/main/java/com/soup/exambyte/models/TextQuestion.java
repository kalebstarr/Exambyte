package com.soup.exambyte.models;


/**
 * Text based variation of a question.
 */
public class TextQuestion implements Question {

  private final QuestionDetails questionDetails;
  private String answer = "";

  /**
   * Constructor for a TextQuestion object with questionTitle and questionDescription.

   * @param questionTitle       The title of a multiple choice question.
   * @param questionDescription The description of a multiple choice question.
   */
  public TextQuestion(String questionTitle, String questionDescription) {
    this.questionDetails = new QuestionDetails(questionTitle, questionDescription);
  }

  public QuestionDetails getQuestionDetails() {
    return questionDetails;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
