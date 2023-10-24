package com.stevanrose.martianrobots;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RobotState {

  int x;
  int y;
  Orientation orientation;

  public String report() {
    return String.format("%d %d %s", x, y, orientation.toString());
  }
}
