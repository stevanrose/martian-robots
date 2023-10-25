package com.stevanrose.martianrobots.controller;

import com.stevanrose.martianrobots.grid.Grid;
import com.stevanrose.martianrobots.robot.Orientation;
import com.stevanrose.martianrobots.robot.Position;
import com.stevanrose.martianrobots.robot.Robot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/martian-robot")
@Slf4j
public class MartianRobotController {

  private Grid grid;

  @PostMapping("/grid")
  public ResponseEntity<Grid> newGrid(@RequestParam(required = true) Integer x, @RequestParam(required = true) Integer y) {
    log.info("Creating new grid {} x {}", x, y);
    grid = new Grid(x, y);
    return ResponseEntity.status(HttpStatus.OK).body(grid);
  }

  @PostMapping("robot")
  public ResponseEntity<String> land(@RequestParam(required = true) String location, @RequestParam(required = true) String commands) {
    log.info("Landing a robot.");
    String[] locationArray = location.split(" ");
    Position position = new Position(Integer.parseInt(locationArray[0]), Integer.parseInt(locationArray[1]));
    Orientation orientation = Orientation.valueOf(locationArray[2]);
    log.info("Start position is {}", location);
    Robot robot = new Robot(grid, position, orientation);
    String report = robot.execute(commands);
    log.info("End position is {}",report);

    return ResponseEntity.status(HttpStatus.OK).body(report);
  }

}
