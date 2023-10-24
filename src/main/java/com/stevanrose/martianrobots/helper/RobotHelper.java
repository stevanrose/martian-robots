package com.stevanrose.martianrobots.helper;

import com.stevanrose.martianrobots.Grid;
import com.stevanrose.martianrobots.Orientation;
import com.stevanrose.martianrobots.Position;
import lombok.experimental.UtilityClass;

import java.util.AbstractMap;
import java.util.Map;

@UtilityClass
public class RobotHelper {

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

  public static Orientation turnRight(Orientation orientation) {
    return rightTurns.get(orientation);
  }

  public static Orientation turnLeft(Orientation orientation) {
    return leftTurns.get(orientation);
  }

  public static Position move(Position position, Orientation orientation) {

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
    return nextPosition;
  }

  public static boolean isOffGrid(Grid grid, Position position) {
    return (position.getX() < 0
        || position.getX() > grid.getUpperX()
        || position.getY() < 0
        || position.getY() > grid.getUpperY());
  }
}
