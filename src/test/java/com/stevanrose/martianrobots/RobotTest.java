package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RobotTest {

  @Test
  void canCreateARobotAndReportCurrentPosition() {
    Grid grid = new Grid(10, 10);
    Position position = new Position(5, 3);
    Robot robot = new Robot(grid, position, Orientation.N);
    assertEquals("5 3 N", robot.report());
  }

  @Test
  void cannotCreateRobotOutsideOfGrid() {
    Grid grid = new Grid(10, 10);
    Position position = new Position(11, 11);
    Exception exception = assertThrows(GridBoundaryException.class, () -> {
      new Robot(grid, position, Orientation.N);
    });

    assertEquals("Robot position (11,11) is beyond the grid boundary (10,10)", exception.getMessage());

  }
}
