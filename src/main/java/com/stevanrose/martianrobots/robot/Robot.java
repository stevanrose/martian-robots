package com.stevanrose.martianrobots.robot;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import com.stevanrose.martianrobots.exception.InvalidCommandException;
import com.stevanrose.martianrobots.grid.Grid;
import lombok.Getter;
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
              position.getX(), position.getY(), grid.getUpperX(), grid.getUpperY()));
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
          log.info("I am lost");
          grid.leaveScent(position);
          return reportLost();
        }
        position.setX(newPosition.getX());
        position.setY(newPosition.getY());
      }
    }
    return report();
  }

  public String report() {
    return String.format("%d %d %s", position.getX(), position.getY(), orientation);
  }

  public String reportLost() {
    return String.format("%d %d %s LOST", position.getX(), position.getY(), orientation);
  }


  private Orientation turnRight(Orientation orientation) {
    Orientation nextOrientation = rightTurns.get(orientation);
    log.info("Turning right from {} to {}", orientation, nextOrientation);
    return nextOrientation;
  }

  private Orientation turnLeft(Orientation orientation) {
    Orientation nextOrientation = leftTurns.get(orientation);
    log.info("Turning left from {} to {}", orientation, nextOrientation);
    return nextOrientation;
  }

  private Position move(Position position, Orientation orientation) {

    log.info("Moving forward");

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
