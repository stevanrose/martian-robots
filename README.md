# martian-robots

Coding problem for Red Badger - Martian Robots -by Steve Rose

## What we're trying to do

The surface of Mars can be modelled by a rectangular grid around which robots are able to
move according to instructions provided from Earth.

This service allow us to define the grid, land a robot within the grid and issue instructions to it.

## Notes
In this implementation of the martian-robot problem I've tried to meet all the requirements in the 
problem description document.

Any input that breaks the rules will cause an exception to be thrown - these exceptions are unhandled 
at the moment.

I've provided a very basic REST interface that allow us to create a grid, to land a robot within the 
grid and to issue commands.

The requirements document mentions...

> There is also a possibility that additional command types may be required in the future and
provision should be made for this.

...I'm going to plead YAGNI on this as we have no idea what these future command type are or how they will 
behave. We'll cross that bridge when we come to it.


## How to build and run me
Built with Java 17, Maven and Spring-Boot.

### Old School

```mvn clean package && java -jar target/martian-robots-0.0.1-SNAPSHOT.jar```

### Maven and spring boot

```mvn clean spring-boot:run```

## How to use me

Build and run me following the steps up there.

### Swagger

Point your browser at the [swagger page](http://localhost:8765/swagger-ui/index.html)

OR

### Use Curl to create a grid

```curl -X 'POST' 'http://localhost:8765/grid?x=5&y=3' -H 'accept: */*'  -d ''```

### Use Curl to land and execute a robot

```curl -X 'POST' 'http://localhost:8765/robot?location=1%201%20E&commands=RFRFRFRF' -H 'accept: */*' -d ''```

## How to run the tests

### From command line using maven

```mvn clean verify```

### In your idea of choice 

I use IntelliJ IDEA, so I right-click the project and run all tests. Your IDE might vary.

## Credits

### .gitignore
Courtesy of [Link Toptal gitignore.io](https://www.toptal.com/developers/gitignore)



