package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RobotTest {

  private Grid grid;
  private Robot robot;
  private Position position;

  @BeforeEach
  void setUp() {
    grid = new Grid(10, 10);
    position = new Position(0, 0);
    robot = new Robot(grid, position, Orientation.N);
  }

  @Test
  void cannotCreateRobotBeyondGridBoundaries() {
    Position position = new Position(11, 11);
    Exception exception =
        assertThrows(
            GridBoundaryException.class,
            () -> {
              new Robot(grid, position, Orientation.N);
            });

    assertEquals(
        "Robot position (11,11) is beyond the grid boundary (10,10)", exception.getMessage());
  }

  @Test
  void canCreateARobotAndReportCurrentPosition() {
    assertEquals("0 0 N", robot.report());
  }

  @Test
  void canInstructRootToTurnToTheRightOnce() {
    assertEquals("0 0 E", robot.execute("R"));
  }

  @Test
  void canInstructRootToTurnToTheRightTwice() {
    assertEquals("0 0 S", robot.execute("RR"));
  }

  @Test
  void canInstructRootToTurnToTheRightThreeTimes() {
    assertEquals("0 0 W", robot.execute("RRR"));
  }

  @Test
  void canInstructRootToTurnToTheRightFourTimes() {
    assertEquals("0 0 N", robot.execute("RRRR"));
  }

  @Test
  void canInstructRootToTurnToTheLeftOnce() {
    assertEquals("0 0 W", robot.execute("L"));
  }

  @Test
  void canInstructRootToTurnToTheLeftTwice() {
    assertEquals("0 0 S", robot.execute("LL"));
  }

  @Test
  void canInstructRootToTurnToTheLeftThreeTimes() {
    assertEquals("0 0 E", robot.execute("LLL"));
  }

  @Test
  void canInstructRootToTurnToTheLeftFourTimes() {
    assertEquals("0 0 N", robot.execute("LLLL"));
  }
}
