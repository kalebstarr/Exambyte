package com.soup.exambyte.service;

import com.soup.exambyte.models.MultipleChoiceQuestion;
import com.soup.exambyte.models.Question;
import com.soup.exambyte.models.TextQuestion;
import java.util.List;

/**
 * Service for managing questions.
 */
public class QuestionService {

  /**
   * Default Constructor.
   */
  public QuestionService() {
  }

  /**
   * Returns a list of questions assigned to a test.

   * @param testId  The id associated with the test.
   * @return        Returns a list of questions.
   */
  public List<Question> getByTestId(int testId) {
    return switch (testId) {
      case 1 -> List.of(
          new TextQuestion("TQuestion 1", "TQuestion Description 1"),
          new MultipleChoiceQuestion("MCQuestion 1", "MCQuestion Description 1")
      );
      case 2 -> List.of(
          new TextQuestion("TQuestion 2", "TQuestion Description 2"),
          new MultipleChoiceQuestion("MCQuestion 2", "MCQuestion Description 2"),
          new TextQuestion("TQuestion 3", "TQuestion Description 3")
      );
      case 3 -> List.of(
          new TextQuestion("TQuestion 4", "TQuestion Description 4")
      );
      case 4 -> List.of(
          new MultipleChoiceQuestion("MCQuestion 3", "MCQuestion Description 3"),
          new MultipleChoiceQuestion("MCQuestion 4", "MCQuestion Description 4"),
          new TextQuestion("TQuestion 5", "TQuestion Description 5"),
          new TextQuestion("TQuestion 6", "TQuestion Description 6")
      );
      case 5 -> List.of(
          new TextQuestion("TQuestion 7", "TQuestion Description 7"),
          new MultipleChoiceQuestion("MCQuestion 5", "MCQuestion Description 5")
      );
      default -> null;
    };
  }
}
