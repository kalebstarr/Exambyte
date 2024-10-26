package com.soup.exambyte.service;

import com.soup.exambyte.models.Test;
import java.util.List;

/**
 * Service for managing tests.
 */
public class TestService {

  /**
   * Default Constructor.
   */
  public TestService() {
  }

  /**
   * Get all tests.

   * @return  Returns a list of all existing tests.
   */
  public List<Test> getAllTests() {
    return List.of(
        new Test(1, "Test 1", "Test 1 Description"),
        new Test(2, "Test 2", "Test 2 Description"),
        new Test(3, "Test 3", "Test 3 Description"),
        new Test(4, "Test 4", "Test 4 Description"),
        new Test(5, "Test 5", "Test 5 Description")
    );
  }

  /**
   * Get a test via its ID.

   * @param testId  The ID associated with the test.
   * @return        Returns a test.
   */
  public Test getTestById(int testId) {
    return switch (testId) {
      case 1 -> new Test(1, "Test 1", "Test 1 Description");
      case 2 -> new Test(2, "Test 2", "Test 2 Description");
      case 3 -> new Test(3, "Test 3", "Test 3 Description");
      case 4 -> new Test(4, "Test 4", "Test 4 Description");
      case 5 -> new Test(5, "Test 5", "Test 5 Description");
      default -> null;
    };
  }
}
