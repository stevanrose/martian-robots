package com.stevanrose.martianrobots;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotTest {

  @Test
  void canCreateARobotAndReportCurrentPosition() {
    RobotState robotState = RobotState.builder().x(5).y(3).orientation(Orientation.N).build();
    Robot robot = Robot.builder().robotState(robotState).build();
    assertEquals("5 3 N", robot.report());
  }
}
