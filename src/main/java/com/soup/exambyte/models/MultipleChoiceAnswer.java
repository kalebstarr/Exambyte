package com.soup.exambyte.models;

import java.util.ArrayList;
import java.util.List;


public class MultipleChoiceAnswer extends Answer {

  private final List<Integer> selectedOptions = new ArrayList<>();

  public MultipleChoiceAnswer(Integer id, Question question) {
    super(question);
  }

  public List<Integer> getSelectedOptions() {
    return selectedOptions;
  }

  public void addSelectedOption(int selectedOption) {
    selectedOptions.add(selectedOption);
  }
}
