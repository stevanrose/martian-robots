package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import lombok.extern.slf4j.Slf4j;

import static com.stevanrose.martianrobots.helper.RobotHelper.*;

@Slf4j
public class Robot {

  private final Grid grid;
  private final Position position;
  private Orientation orientation;

  public Robot(Grid grid, Position position, Orientation orientation) {

    if (position.getX() > grid.getUpperX() || position.getY() > grid.getUpperY()) {
      throw new GridBoundaryException(
          String.format(
              "Robot position (%d,%d) is beyond the grid boundary (%d,%d)",
              position.getX(), position.getY(), grid.getUpperX(), grid.getUpperX()));
    }

    this.grid = grid;
    this.position = position;
    this.orientation = orientation;
  }

  public String execute(String commands) {

    for (char c : commands.toCharArray()) {
      String command = String.valueOf(c);
      if (command.equals("R")) {
        orientation = turnRight(orientation);
      }
      if (command.equals("L")) {
        orientation = turnLeft(orientation);
      }
      if (command.equals("F")) {
        Position newPosition = move(position, orientation);
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());
      }
    }
    return report();
  }

  public String report() {
    if(isOffGrid(grid, position)) {
      return String.format("%d %d %s LOST", position.getX(), position.getY(), orientation);
    } else {
      return String.format("%d %d %s", position.getX(), position.getY(), orientation);
    }
  }
}
