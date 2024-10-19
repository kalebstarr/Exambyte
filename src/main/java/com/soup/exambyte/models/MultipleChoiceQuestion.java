package com.soup.exambyte.models;

import java.util.HashMap;
import java.util.Map;

public class MultipleChoiceQuestion extends Question{

  private final Map<String, Boolean> options = new HashMap<>();

  public MultipleChoiceQuestion(String questionTitle, String questionDescription) {
    super(questionTitle, questionDescription);
  }

  public Map<String, Boolean> getOptions() {
    return options;
  }

  public void addOptions(String option, boolean correct) {
    options.put(option, correct);
  }
}
