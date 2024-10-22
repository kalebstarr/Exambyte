package com.soup.exambyte.models;

import java.util.HashMap;
import java.util.Map;


/**
 * Multiple choice variation of a question.
 */
public class MultipleChoiceQuestion implements Question {

  private final QuestionDetails questionDetails;
  private final Map<String, Boolean> options = new HashMap<>();

  /**
   * Constructor for a MultipleChoiceQuestion object with questionTitle and questionDescription.

   * @param questionTitle       The title of a multiple choice question.
   * @param questionDescription The description of a multiple choice question.
   */
  public MultipleChoiceQuestion(String questionTitle, String questionDescription) {
    this.questionDetails = new QuestionDetails(questionTitle, questionDescription);
  }

  public QuestionDetails getQuestionDetails() {
    return questionDetails;
  }

  public Map<String, Boolean> getOptions() {
    return options;
  }

  /**
   * Adds an option to the multiple choice question.

   * @param optionText  The text of an option.
   * @param correct     The correctness of an option.
   */
  public void addOptions(String optionText, boolean correct) {
    options.put(optionText, correct);
  }
}
