package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Grid {

  private int upperX;
  private int upperY;
  private List<Position> scents = new ArrayList<>();

  public Grid(int upperX, int upperY) {

    if (upperX > 50 || upperY > 50) {
      throw new GridBoundaryException("Upper limits grid (50,50) exceeded");
    }
    this.upperX = upperX;
    this.upperY = upperY;
  }

  public void leaveScent(Position position) {
    scents.add(position);
  }

  public boolean hasScent(Position position) {
    return scents.stream().anyMatch(s -> s.getX() == position.getX() && s.getY() == position.getY());
  }
}
