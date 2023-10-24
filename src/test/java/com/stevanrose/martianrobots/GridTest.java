package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
