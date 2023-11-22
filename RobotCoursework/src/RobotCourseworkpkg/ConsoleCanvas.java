package RobotCourseworkpkg;

//////package RobotCourseworkpkg;

/**
 * Class that defines the canvas that the RobotArena exists within. This canvas
 * is what's displayed on the console when the program is ran. It has functions to
 * construct the arena, display the canvas and display new Robots added to the canvas.
 * @author Hugo O.D.
 */
public class ConsoleCanvas {

    private String[][] canvas; //String array of arena drawing
    private int xMax; //Width of arena
    private int yMax; //Height of arena

    /**
     * Constructor for ConsoleCanvas Class.
     * Sets arena's width and height, and populates the canvas string array
     * with a border of hashes and other elements with spaces. It then centres
     * a student number on the top of the border.
     * @param width
     * @param height
     * @param studNo 
     */
    public ConsoleCanvas(int width, int height, String studNo) {
        xMax = width;
        yMax = height;
        canvas = new String[yMax][xMax]; //Make string array with width/height 
        for (int i = 0; i < yMax; i++) { //loop through every y element
            for (int j = 0; j < xMax; j++) { //loop through every x element
                if (i == 0 || j == 0 || i == yMax - 1 || j == xMax - 1) { //If indx is part of border
                    canvas[i][j] = "#"; //populate with hash
                } else { 
                    canvas[i][j] = " "; //populate with empty space
                }
            }
        }

        int startOfNo = (xMax - studNo.length()) / 2; //finds the start of student number
        for (int i = startOfNo; i < startOfNo + studNo.length(); i++) { //loop from start to end of student number
            //fill top line of canvas with string value of each number in student number
            canvas[0][i] = String.valueOf(studNo.charAt(i - startOfNo)); 
        }

    }

    /**
     * Fills the current canvas with an object (typically a robot), if it's within
     * the bounds of the arena
     * @param x x coordinate of robot
     * @param y y coordinate of robot
     * @param robType Type of robot
     */ 
    public void showIt(int x, int y, String robType) {
        if (x > xMax - 1 || y > yMax - 1 || x < 1 || y < 1) { //If position is outside border
            System.out.println("Invalid position");
        } else {
            canvas[y][x] = robType; //shows string on canvas
        }
    }
    
    /**
     * Returns the current canvas containing the current arena and all of it's contained
     * objects as a string.
     * @return 
     */    
    @Override
    public String toString() {
        String res = "";
        for (String[] canRow : canvas) { //loop through each row in canvas
            for (String canCol : canRow) { //loop through each col in canvas
                res += canCol; //add canvas line to result string
            }
            res += "\n"; //add line break after each col to show new row
        }

        return res;
    }

    public static void main(String[] args) {
        ConsoleCanvas cc = new ConsoleCanvas(16, 8, "31005504");
        cc.showIt(3, 2, "R");
        System.out.println(cc.toString());
    }

}
