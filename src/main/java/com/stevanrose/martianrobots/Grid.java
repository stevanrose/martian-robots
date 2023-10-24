package com.stevanrose.martianrobots;

import com.stevanrose.martianrobots.exception.GridBoundaryException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grid {

    private int upperX;
    private int upperY;

    public Grid(int upperX, int upperY) {

        if(upperX > 50 || upperY > 50) {
            throw new GridBoundaryException("Upper limits grid (50,50) exceeded");
        }
        this.upperX = upperX;
        this.upperY = upperY;

    }
}
