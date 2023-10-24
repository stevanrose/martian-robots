package com.stevanrose.martianrobots.helper;

import com.stevanrose.martianrobots.Orientation;
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
}
