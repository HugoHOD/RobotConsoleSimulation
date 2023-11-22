package RobotCourseworkpkg;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that defines the arena that Robots exist within. This class also defines
 * robot movement, adding robots and displaying robots.
 * @author Hugo O.D. 31005504
 */
public class RobotArena {

    private int xMax, yMax; //Declares height & width of arena
    ArrayList<Robot> roboList; //ArrayList of Robot objects
    private Random randGen; //Random used for random robot placement
    
    /**
     * RobotArena constructor, initialises ArrayList of Robot objects,
     * sets parameter arena size to new arena object size and
     * initialises Random object to be used for robot placement 
     * @param width Width of arena
     * @param height Height of arena
     */
    public RobotArena(int width, int height) {
        roboList = new ArrayList<>();
        xMax = width;
        yMax = height;
        randGen = new Random();
    }

    
    /**
     * Adds a robot to the current arena.
     * Generates two random numbers for x & y values of the robot to be initialised,
     * uses getRobotAt to check if any other robots occupy that position,
     * if no robots are found there, the new robot takes the coordinates of the 
     * random numbers & random direction, if robots are found there, do-while loop 
     * activates and new random numbers are generated
     */
    public void addRobot() {
        boolean roboThere = true;
        do {
            int randX = randGen.nextInt(xMax-1-1)+1; //generate random number with
            int randY = randGen.nextInt(yMax-1-1)+1; //bounds of arena size
            if (getRobotAt(randX, randY) == null) { //if there isnt a robot there
                roboThere = false;
                Robot r = new Robot(randX, randY, Robot.getRandDirection()); //inits new robot with rand x,y and direction
                roboList.add(r);
            }
        } while (roboThere == true);
    }
    
     /**
     * Loops through roboList and checks if any existing robot exists within 
     * the passed x & y values
     * @param x Passed x co-ordinate of robot to be initialised
     * @param y Passed y co-ordinate of robot to be initialised
     * @return The robot that holds the passed x & y value if exists, else returns null
     */
    
    public Robot getRobotAt(int x, int y) {
        for (int i = 0; i < roboList.size(); i++) {
            if (roboList.get(i).isHere(x, y) == true) { //Uses isHere to check if Robot i has these coords
                return roboList.get(i); //if so, return Robot i
            }
        }
        return null; //if not, return null
    }
    
    /**
     * Returns if the next x & y coordinates are a valid position for a robot to
     * be in. It first checks if the next coordinates are within the bounds of the
     * arena, then checks if any other robot is at that position.
     * @param nextX Next x coordinate that a robot wants to take
     * @param nextY Next y coordinate that a robot wants to take
     * @return True if a robot can move there, false if it cannot
     */
    
    public boolean canMoveHere(int nextX, int nextY) {
        if (nextX > xMax - 2 || nextY > yMax -2 || nextX < 1 || nextY < 1) {
            return false;
        } else {
            return getRobotAt(nextX, nextY) == null;
        }
    }
    
    /**
     * Displays all robots in the current arena by calling displayRobot in ConsoleCanvas
     * @param c 
     */
    public void showRobots(ConsoleCanvas c){
        for (Robot robots : roboList) {
            robots.displayRobot(c);
        }
    }
    
    /**
     * Moves all robots their next respective positions once by calling tryMoveHere 
     * for all robots in the arena
     */    
    public void moveAllRobots(){
        for (Robot robot : roboList) {
            robot.tryMoveHere(this);
        }
    }
    
    /**
     * Creates string that states the size of the current arena, 
     * loops through roboList and calls Robot's toString method for each robot in list
     * @return Concatenated String result of arena size and robot information
     */    
    @Override
    public String toString() {
        String res = "The size of the arena is " + xMax + " by " + yMax + "\n";
        for (int i = 0; i < roboList.size(); i++) {
            res += roboList.get(i).toString(); //Add Robot.toString to resultant string
        }
        return res;
    }
    
    /**
     * Creates string of the current arena's size and the positioning information
     * of each robot in the current arena, used for file processing
     * @return String containing arena's info and robots within arena's info
     */   
    public String toRawString() {
        String res = xMax + " " + yMax + ";";
        for (int i = 0; i < roboList.size(); i++) {
            res += roboList.get(i).toRawString() + ";";
        }
        return res;
    }
    
    /**
     * Adds new robot to the roboList ArrayList, used when adding Robot from a 
     * file rather than using addRobot
     * @param r Robot to be added
     */
    public void addRobToList(Robot r){
        roboList.add(r);
    }
    
    /**
     * Gets the width of current arena
     * @return The size in x of the arena
     */
    public int getxMax() {
        return xMax;
    }
 /**
     * Gets the height of current arena
     * @return The size in y of the arena
     */
    public int getyMax() {
        return yMax;
    }
    
    public static void main(String[] args) { //Redundant main used for testing class methods
        RobotArena a = new RobotArena(20, 10);
        a.addRobot();
        a.addRobot();
        a.addRobot();
        System.out.println(a.toString());
    }

}
