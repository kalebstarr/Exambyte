package com.soup.exambyte.models;

/**
 * Simple interface for a Question.
 */
public interface Question {

  /**
   * Getter for QuestionDetails within a given Question variation.

   * @return QuestionDetails object.
   */
  QuestionDetails getQuestionDetails();
  String getQuestionTitle();
  String getQuestionDescription();
}
