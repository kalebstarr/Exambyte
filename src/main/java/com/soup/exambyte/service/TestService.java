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

  public List<Test> getAllTests() {
    return List.of(
        new Test(1, "Test 1", "Test 1 Description"),
        new Test(2, "Test 2", "Test 2 Description"),
        new Test(3, "Test 3", "Test 3 Description"),
        new Test(4, "Test 4", "Test 4 Description"),
        new Test(5, "Test 5", "Test 5 Description")
    );
  }
}
