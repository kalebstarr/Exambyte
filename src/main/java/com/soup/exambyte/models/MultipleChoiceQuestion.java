package com.soup.exambyte.models;

import java.util.ArrayList;
import java.util.List;


/**
 * Multiple choice variation of a question.
 */
public class MultipleChoiceQuestion extends Question {

  private List<String> options = new ArrayList<>();
  private List<Integer> correctOptionIndices = new ArrayList<>();

  /**
   * Constructor for a MultipleChoiceQuestion object with questionTitle and questionDescription.

   * @param questionTitle       The title of a multiple choice question.
   * @param questionDescription The description of a multiple choice question.
   */
  public MultipleChoiceQuestion(String questionTitle, String questionDescription) {
    super(questionTitle, questionDescription);
  }

  public MultipleChoiceQuestion(String questionTitle, String questionDescription,
      List<String> options, List<Integer> correctOptions) {
    super(questionTitle, questionDescription);
    this.options = options;
    this.correctOptionIndices = correctOptions;
  }

  public List<String> getOptions() {
    return options;
  }

  public List<Integer> getCorrectOptionIndices() {
    return correctOptionIndices;
  }

  public void addOption(String option) {
    options.add(option);
  }

  public void addCorrectOptionIndex(int correctOptionIndex) {
    correctOptionIndices.add(correctOptionIndex);
  }

  public void addOptionAndStatus(String option, boolean isCorrect) {
    options.add(option);
    if (isCorrect) {
      correctOptionIndices.add(options.size() - 1);
    }
  }
}
