package com.stevanrose.martianrobots;

import static org.junit.jupiter.api.Assertions.*;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  void canCreateAGridSuccessfully() {
    assertDoesNotThrow(() -> new Grid(10, 10));
  }

  @Test
  void cannotCreateGridGreaterThanMaximumSize() {
    Exception exception = assertThrows(GridBoundaryException.class, () -> {
      new Grid(51, 51);
    });

    assertEquals("Upper limits grid (50,50) exceeded", exception.getMessage());
  }
}
