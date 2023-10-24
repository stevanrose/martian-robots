package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;

public class Robot {

  private final Grid grid;
  private final Position position;
  private final Orientation orientation;

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

  public String report() {
    return String.format("%d %d %s", position.getX(), position.getY(), orientation);
  }
}
