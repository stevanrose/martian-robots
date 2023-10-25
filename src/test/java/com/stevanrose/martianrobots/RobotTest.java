package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    position = new Position(11, 11);
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
  void cannotCreateRobotBelowGridBoundaries() {
    position = new Position(-1, -1);
    Exception exception =
        assertThrows(
            GridBoundaryException.class,
            () -> {
              new Robot(grid, position, Orientation.N);
            });

    assertEquals(
        "Robot position (-1,-1) is beyond the grid boundary (10,10)", exception.getMessage());
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
    assertEquals("5 6 N", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpaceNorthWhilstRemainingInTheGrid() {
    assertEquals("5 7 N", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceEastWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("6 5 E", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpacesEastWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("7 5 E", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceSouthWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 4 S", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpacesSouthWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 3 S", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceWestWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("4 5 W", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpaceWestWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("3 5 W", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOffGridNorthAndReportItselfLostAndLeavesAScent() {
    position = new Position(5, 10);
    robot = new Robot(grid, position, Orientation.N);
    assertEquals("5 11 N LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void canInstructRobotToMoveOffGridEastAndReportItselfLostAndLeavesAScent() {
    position = new Position(10, 5);
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("11 5 E LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void canInstructRobotToMoveOffGridSouthAndReportItselfLostAndLeavesAScent() {
    position = new Position(5, 0);
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 -1 S LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void canInstructRobotToMoveOffGridWestAndReportItselfLostAndLeavesAScent() {
    position = new Position(0, 5);
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("-1 5 W LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void cannotInstructRobotToMoveOffGridIfCurrentPositionContainsAScent() {
    position = new Position(5, 10);
    grid.leaveScent(position);
    robot = new Robot(grid, position, Orientation.N);
    assertEquals("5 10 N", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveOnGridIfCurrentPositionContainsAScent() {
    position = new Position(5, 10);
    grid.leaveScent(position);
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 9 S", robot.execute("F"));
  }
}
