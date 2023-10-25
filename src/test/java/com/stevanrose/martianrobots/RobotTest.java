package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import com.stevanrose.martianrobots.exception.InvalidCommandException;
import com.stevanrose.martianrobots.grid.Grid;
import com.stevanrose.martianrobots.robot.Orientation;
import com.stevanrose.martianrobots.robot.Position;
import com.stevanrose.martianrobots.robot.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

  private Grid grid;
  private Robot robot;
  private Position position;

  @BeforeEach
  void setUp() {
    grid = new Grid(5, 3);
    position = new Position(1, 1);
    robot = new Robot(grid, position, Orientation.N);
  }

  @Test
  void cannotCreateRobotBeyondGridBoundaries() {
    position = new Position(6, 4);
    Exception exception =
        assertThrows(
            GridBoundaryException.class,
            () -> {
              new Robot(grid, position, Orientation.N);
            });

    assertEquals("Robot position (6,4) is beyond the grid boundary (5,3)", exception.getMessage());
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
        "Robot position (-1,-1) is beyond the grid boundary (5,3)", exception.getMessage());
  }

  @Test
  void cannotIssueMoreThan99Commands() {
    String commands = "LF".repeat(51);
    Exception exception =
        assertThrows(
            InvalidCommandException.class,
            () -> {
              robot.execute(commands);
            });

    assertEquals(
        "Robot can only execute up to 99 instructions (L, R or F)", exception.getMessage());
  }

  @Test
  void CannotIssueInvalidCommands() {
    String commands = "XYZ";
    Exception exception =
        assertThrows(
            InvalidCommandException.class,
            () -> {
              robot.execute(commands);
            });

    assertEquals(
        "Robot can only execute up to 99 instructions (L, R or F)", exception.getMessage());
  }

  @Test
  void canCreateARobotAndReportCurrentPosition() {
    assertEquals("1 1 N", robot.report());
  }

  @Test
  void canInstructRootToTurnToTheRightOnce() {
    assertEquals("1 1 E", robot.execute("R"));
  }

  @Test
  void canInstructRootToTurnToTheRightTwice() {
    assertEquals("1 1 S", robot.execute("RR"));
  }

  @Test
  void canInstructRootToTurnToTheRightThreeTimes() {
    assertEquals("1 1 W", robot.execute("RRR"));
  }

  @Test
  void canInstructRootToTurnToTheRightFourTimes() {
    assertEquals("1 1 N", robot.execute("RRRR"));
  }

  @Test
  void canInstructRootToTurnToTheLeftOnce() {
    assertEquals("1 1 W", robot.execute("L"));
  }

  @Test
  void canInstructRootToTurnToTheLeftTwice() {
    assertEquals("1 1 S", robot.execute("LL"));
  }

  @Test
  void canInstructRootToTurnToTheLeftThreeTimes() {
    assertEquals("1 1 E", robot.execute("LLL"));
  }

  @Test
  void canInstructRootToTurnToTheLeftFourTimes() {
    assertEquals("1 1 N", robot.execute("LLLL"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceNorthWhilstRemainingInTheGrid() {
    assertEquals("1 2 N", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpaceNorthWhilstRemainingInTheGrid() {
    assertEquals("1 3 N", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceEastWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("2 1 E", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpacesEastWhilstRemainingInTheGrid() {
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("3 1 E", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceSouthWhilstRemainingInTheGrid() {
    position = new Position(1, 2);
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("1 1 S", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpacesSouthWhilstRemainingInTheGrid() {
    position = new Position(1, 3);
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("1 1 S", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOneSpaceWestWhilstRemainingInTheGrid() {
    position = new Position(2, 1);
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("1 1 W", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveTwoSpaceWestWhilstRemainingInTheGrid() {
    position = new Position(3, 1);
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("1 1 W", robot.execute("FF"));
  }

  @Test
  void canInstructRobotToMoveOffGridNorthAndReportItselfLostAndLeavesAScent() {
    position = new Position(5, 3);
    robot = new Robot(grid, position, Orientation.N);
    assertEquals("5 3 N LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void canInstructRobotToMoveOffGridEastAndReportItselfLostAndLeavesAScent() {
    position = new Position(5, 3);
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("5 3 E LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void canInstructRobotToMoveOffGridSouthAndReportItselfLostAndLeavesAScent() {
    position = new Position(5, 0);
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 0 S LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void canInstructRobotToMoveOffGridWestAndReportItselfLostAndLeavesAScent() {
    position = new Position(0, 1);
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("0 1 W LOST", robot.execute("F"));
    assertTrue(grid.hasScent(position));
  }

  @Test
  void cannotInstructRobotToMoveOffGridIfCurrentPositionContainsAScent() {
    position = new Position(5, 3);
    grid.leaveScent(position);
    robot = new Robot(grid, position, Orientation.N);
    assertEquals("5 3 N", robot.execute("F"));
  }

  @Test
  void canInstructRobotToMoveOnGridIfCurrentPositionContainsAScent() {
    position = new Position(5, 3);
    grid.leaveScent(position);
    robot = new Robot(grid, position, Orientation.S);
    assertEquals("5 2 S", robot.execute("F"));
  }

  @Test
  void canInstructRobotToFollowSampleInput1() {
    position = new Position(1, 1);
    robot = new Robot(grid, position, Orientation.E);
    assertEquals("1 1 E", robot.execute("RFRFRFRF"));
  }

  @Test
  void canInstructRobotToFollowSampleInput2() {
    position = new Position(3, 2);
    robot = new Robot(grid, position, Orientation.N);
    assertEquals("3 3 N LOST", robot.execute("FRRFLLFFRRFLL"));
  }

  @Test
  void canInstructRobotToFollowSampleInput3() {
    position = new Position(0, 3);
    grid.leaveScent(new Position(3, 3));
    robot = new Robot(grid, position, Orientation.W);
    assertEquals("2 3 S", robot.execute("LLFFFLFLFL"));
  }
}
