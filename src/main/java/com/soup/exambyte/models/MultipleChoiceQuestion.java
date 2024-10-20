package com.soup.exambyte.models;

import java.util.HashMap;
import java.util.Map;


/**
 * Model for a multiple choice variation of the question object.
 */
public class MultipleChoiceQuestion extends Question {

  private final Map<String, Boolean> options = new HashMap<>();

  /**
   * Constructor for a MultipleChoiceQuestion object with questionTitle and questionDescription.

   * @param questionTitle       The title of a multiple choice question.
   * @param questionDescription The description of a multiple choice question.
   */
  public MultipleChoiceQuestion(String questionTitle, String questionDescription) {
    super(questionTitle, questionDescription);
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
