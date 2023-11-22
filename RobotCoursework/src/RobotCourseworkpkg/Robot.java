package RobotCourseworkpkg;

import java.util.Random;
import java.lang.Enum;

/**
 * Robot class that defines Robots, existing within a RobotArena, that have the
 * ability to be displayed, move and change direction.
 *
 * @author Hugo O.D. 31005504
 */
public class Robot {

    private static Random rand = new Random(); //Random number generator
    private static int robotCount = 0; //Class var to keep track of number of robots/assign robotID
    private int x, y, robotID; //Declares robot's x & y co-ords in arena and it's ID number
    private Direction currentDir; //Robot's current direction

    /**
     * Enumeration class for robot's cardinal directions. Determines their
     * heading and where they will go on their next move
     */
    public enum Direction {
        NORTH, EAST, SOUTH, WEST

    }

    /**
     * Constructs a new instance of Robot. Initialises x & y values to given x &
     * y values (rx & ry) as well as assigning it's current direction to be the
     * passed direction. Sets robotID to be the value of the number of robots
     * and increments number of robots
     *
     * @param rx x co-ordinate of new robot
     * @param ry y co-ordinate of new robot
     * @param initialDir new robot's initial direction
     */
    public Robot(int rx, int ry, Direction initialDir) {
        x = rx;
        y = ry;
        currentDir = initialDir;
        robotID = robotCount++;
    }

    /**
     * Constructs a new instance of Robot. An alternative constructor for Robot
     * used by file handling blocks to load an arena from a file. It splits the
     * passed raw data into a string array and assigns it's x,y and Direction
     * values from each element in the string array, it also assigns it's
     * robotID to be an incremented robotCount.
     *
     * @param data String containing raw data of a Robot exported from a file
     */
    public Robot(String data) {
        String[] splitData = data.split(" ");
        x = Integer.parseInt(splitData[0]);
        y = Integer.parseInt(splitData[1]);
        currentDir = Direction.valueOf(splitData[2]);
        robotID = robotCount++;
    }

    /**
     * Attempts to move robot to next location according to it's direction. It
     * first finds it's next x and y coordinates according to it's direction. If
     * this move is a valid move, it assigns it's next x and y coordinates to be
     * it's current coordinates, if not then it assigns it's current direction
     * to be it's next direction
     *
     * @param arena
     */
    public void tryMoveHere(RobotArena arena) {
        int nextX = x;
        int nextY = y;
        switch (currentDir) {
            case NORTH:
                nextY = y - 1;
                break;
            case EAST:
                nextX = x + 1;
                break;
            case SOUTH:
                nextY = y + 1;
                break;
            case WEST:
                nextX = x - 1;
        }
        //Calls canMoveHere in RobotArena with next x,y coords and current direction to see if it's a valid move
        boolean validMove = arena.canMoveHere(nextX, nextY);
        if (validMove) {
            x = nextX;
            y = nextY;
        } else {
            currentDir = getNextDirection(currentDir);
        }
    }

    /**
     * Checks if passed x & y values are equal to this instance's x & y values
     *
     * @param checkX Given x coordinate
     * @param checkY Given y coordinate
     * @return True if both x & y coordinates are equal, false if not
     */
    public boolean isHere(int checkX, int checkY) {
        return checkX == x && checkY == y;
    }

    /**
     * Gets the next Direction enum value given a current direction. It first
     * gets the index of the current direction, then finds the index of the next
     * direction and returns the value associated with that index
     *
     * @param currentDir Current direction
     * @return Next direction
     */
    public Direction getNextDirection(Direction currentDir) {
        Direction[] dirs = Direction.values(); //Creates array of direction values in order of declaration
        int indx = 0;
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i] == currentDir) { //finds index of current direction
                indx = i; //Assigns i value to index
                break;
            }
        }
        int nextIndx = (indx + 1) % dirs.length; //Finds next direction index, if current direction is the last value in array,
        //then wraps around to first value using mod operator
        return dirs[nextIndx]; //returns direction at nextIndx
    }

    /**
     * Returns a random enum value of class Direction. This creates an array of
     * enum values in the order they're declared and chooses a random value from
     * this list
     *
     * @return
     */
    public static Direction getRandDirection() {
        return Direction.values()[rand.nextInt(4)];
    }

    /**
     * Displays robot on canvas specified by c, by calling the showIt method to
     * populating the canvas at index x,y with the string "R"
     *
     * @param c Current canvas
     */
    public void displayRobot(ConsoleCanvas c) {
        c.showIt(x, y, "R");
    }

    /**
     * Returns a string that states which robot, specified by robotID, is at
     * what coordinates, specified by x and y, and what direction it's moving
     *
     * @return String stating which robot is at what co-ordinates
     */
    @Override
    public String toString() {
        return "Robot " + robotID + " is at " + x + ", " + y + " and moving "
                + currentDir.toString() + "\n";
    }

    /**
     * Returns string with x and y coordinates of this instansiation of robot
     * with it's current direction, used for text file processing
     *
     * @return String containing raw information about current robot
     */
    public String toRawString() {
        return x + " " + y + " " + currentDir.toString();
    }

    public static void main(String[] args) { //Redundant main used for testing class methods
        System.out.println("Length of Direction.values() = " + Direction.values().length);
        Robot r1 = new Robot(5, 3, Direction.EAST);
        Robot r2 = new Robot(8, 2, Direction.SOUTH);
        System.out.println(r1.toString());
        System.out.println(r2.toString());
    }

}
