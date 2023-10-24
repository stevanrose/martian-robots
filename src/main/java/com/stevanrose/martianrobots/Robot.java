package com.stevanrose.martianrobots;

import lombok.Builder;

@Builder
public class Robot {

  private RobotState robotState;

  public String report() {
    return robotState.report();
  }
  
}
