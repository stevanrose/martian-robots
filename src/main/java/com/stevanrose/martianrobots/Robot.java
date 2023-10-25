package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import com.stevanrose.martianrobots.exception.InvalidCommandException;
import lombok.extern.slf4j.Slf4j;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Robot {

  private static final Map<Orientation, Orientation> rightTurns =
      Map.ofEntries(
          new AbstractMap.SimpleImmutableEntry<>(Orientation.N, Orientation.E),
          new AbstractMap.SimpleImmutableEntry<>(Orientation.E, Orientation.S),
          new AbstractMap.SimpleImmutableEntry<>(Orientation.S, Orientation.W),
          new AbstractMap.SimpleImmutableEntry<>(Orientation.W, Orientation.N));
  private static final Map<Orientation, Orientation> leftTurns =
      Map.ofEntries(
          new AbstractMap.SimpleImmutableEntry<>(Orientation.N, Orientation.W),
          new AbstractMap.SimpleImmutableEntry<>(Orientation.W, Orientation.S),
          new AbstractMap.SimpleImmutableEntry<>(Orientation.S, Orientation.E),
          new AbstractMap.SimpleImmutableEntry<>(Orientation.E, Orientation.N));

  private final Grid grid;
  private final Position position;
  private Orientation orientation;

  public Robot(Grid grid, Position position, Orientation orientation) {
    this.grid = grid;
    this.position = position;
    this.orientation = orientation;

    if (isOffGrid(position)) {
      throw new GridBoundaryException(
          String.format(
              "Robot position (%d,%d) is beyond the grid boundary (%d,%d)",
              position.getX(), position.getY(), grid.getUpperX(), grid.getUpperX()));
    }
  }

  public String execute(String commands) {

    validate(commands);

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
        if (isOffGrid(newPosition)) {
          grid.leaveScent(position);
        }
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());
      }
    }
    return report();
  }

  public String report() {
    if (isOffGrid(position)) {
      return String.format("%d %d %s LOST", position.getX(), position.getY(), orientation);
    } else {
      return String.format("%d %d %s", position.getX(), position.getY(), orientation);
    }
  }

  private Orientation turnRight(Orientation orientation) {
    return rightTurns.get(orientation);
  }

  private Orientation turnLeft(Orientation orientation) {
    return leftTurns.get(orientation);
  }

  private Position move(Position position, Orientation orientation) {

    Position nextPosition = new Position(position.getX(), position.getY());

    if (orientation.equals(Orientation.N)) {
      nextPosition.setY(position.getY() + 1);
    }
    if (orientation.equals(Orientation.E)) {
      nextPosition.setX(position.getX() + 1);
    }
    if (orientation.equals(Orientation.S)) {
      nextPosition.setY(position.getY() - 1);
    }
    if (orientation.equals(Orientation.W)) {
      nextPosition.setX(position.getX() - 1);
    }
    if (grid.hasScent(position) && isOffGrid(nextPosition)) {
      log.info("Instruction to move robot off grid from scented position ignored");
      return position;
    }
    return nextPosition;
  }



  private boolean isOffGrid(Position position) {
    return (position.getX() < 0
        || position.getX() > grid.getUpperX()
        || position.getY() < 0
        || position.getY() > grid.getUpperY());
  }

  private void validate(String commands) {
    Pattern pattern = Pattern.compile("^[LRF]{1,99}$");
    Matcher matcher = pattern.matcher(commands);
    if (!matcher.find()) {
      throw new InvalidCommandException("Robot can only execute up to 99 instructions (L, R or F)");
    }
  }

}
