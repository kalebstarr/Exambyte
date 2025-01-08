package com.soup.exambyte.dto;

import com.soup.exambyte.models.QuestionType;
import java.util.List;

public class AnswerForm {

  private QuestionType questionType;
  private String questionAnswer;
  private List<Integer> selectedOptions;

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

  public String getQuestionAnswer() {
    return questionAnswer;
  }

  public void setQuestionAnswer(String questionAnswer) {
    this.questionAnswer = questionAnswer;
  }

  public List<Integer> getSelectedOptions() {
    return selectedOptions;
  }

  public void setSelectedOptions(List<Integer> selectedOptions) {
    this.selectedOptions = selectedOptions;
  }
}
