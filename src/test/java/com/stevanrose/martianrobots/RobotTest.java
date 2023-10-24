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
    position = new Position(5, 5);
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
    assertEquals("5 5 N", robot.report());
  }

  @Test
  void canInstructRootToTurnToTheRightOnce() {
    assertEquals("5 5 E", robot.execute("R"));
  }

  @Test
  void canInstructRootToTurnToTheRightTwice() {
    assertEquals("5 5 S", robot.execute("RR"));
  }

  @Test
  void canInstructRootToTurnToTheRightThreeTimes() {
    assertEquals("5 5 W", robot.execute("RRR"));
  }

  @Test
  void canInstructRootToTurnToTheRightFourTimes() {
    assertEquals("5 5 N", robot.execute("RRRR"));
  }

  @Test
  void canInstructRootToTurnToTheLeftOnce() {
    assertEquals("5 5 W", robot.execute("L"));
  }

  @Test
  void canInstructRootToTurnToTheLeftTwice() {
    assertEquals("5 5 S", robot.execute("LL"));
  }

  @Test
  void canInstructRootToTurnToTheLeftThreeTimes() {
    assertEquals("5 5 E", robot.execute("LLL"));
  }

  @Test
  void canInstructRootToTurnToTheLeftFourTimes() {
    assertEquals("5 5 N", robot.execute("LLLL"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceNorthWhilstRemainingInTheGrid() {
    assertEquals("5 6 N", robot.execute("M"));
  }

  @Test
  void canInstructRobotToMoveTwoSpaceNorthWhilstRemainingInTheGrid() {
    assertEquals("5 7 N", robot.execute("MM"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceEastWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("6 5 E", robot.execute("M"));
  }

  @Test
  void canInstructRobotToMoveTwoSpacesEastWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("7 5 E", robot.execute("MM"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceSouthWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 4 S", robot.execute("M"));
  }

  @Test
  void canInstructRobotToMoveTwoSpacesSouthWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 3 S", robot.execute("MM"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceWestWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("4 5 W", robot.execute("M"));
  }

  @Test
  void canInstructRobotToMoveTwoSpaceWestWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("3 5 W", robot.execute("MM"));
  }

}
