package RobotCourseworkpkg;

/**
 * Simple program to show arena with multiple robots
 *
 * @author shsmchlr
 * @author Hugo O.D.
 */
import java.util.Scanner;

public class RobotInterface {

    private Scanner input; // scanner used for input from user
    private RobotArena myArena; // arena in which Robots are shown
    private TextFile tf; //Textfile object for text file processing

    /**
     * Constructor for RobotInterface, sets up scanner used for input and the
     * arena then has main loop allowing user to enter commands
     *
     * @throws java.lang.InterruptedException
     */
    public RobotInterface() throws InterruptedException {
        input = new Scanner(System.in); // set up scanner for user input
        myArena = new RobotArena(20, 6); // create arena of size 20*6
        tf = new TextFile("Text Files", "txt"); //set up textfile object
        char response;
        do {
            System.out.println("Enter (A)dd Robot, (M)ove robots, "
                    + "(S)how information/display, (C)reate new arena, "
                    + "save/load to/from (F)ile or e(X)it:");
            response = input.next().charAt(0);
            input.nextLine();
            switch (response) {
                //Add Robot block
                case 'A':
                case 'a':
                    myArena.addRobot(); // add a new Robot to arena
                    System.out.println("Robot Added");
                    break;
                //Move Robots menu
                case 'M':
                case 'm':
                    System.out.println("Enter Move robots (O)nce, move robots "
                            + "(n) times or (B)ack");
                    switch (input.next().charAt(0)) {
                        //Move robots once block
                        case 'O':
                        case 'o':
                            myArena.moveAllRobots();
                            doDisplay();
                            System.out.println(myArena.toString());
                            break;
                        //Move robots 10 times block
                        case 'N':
                        case 'n':
                            System.out.println("Move robots how many times?");
                            int moveTimes = input.nextInt();
                            for (int i = 0; i < moveTimes; i++) {
                                myArena.moveAllRobots();
                                doDisplay();
                                System.out.println(myArena.toString());
                                Thread.sleep(600);
                            }
                            break;
                    }
                    break; //no need to implement B case as it will break under any other case
                //Show information/Display menu
                case 'S':
                case 's':
                    System.out.println("Enter show (D)isplay or show (I)nfo, or (B)ack");
                    switch (input.next().charAt(0)) {
                        //Show display block
                        case 'D':
                        case 'd':
                            doDisplay();
                            break;
                        //Show Information block
                        case 'I':
                        case 'i':
                            System.out.println(myArena.toString());
                            break;
                    }
                    break; //no need to implement B case as it will break under any other case
                //Create new arena block
                case 'C':
                case 'c':
                    System.out.println("Enter new arena dimensions (x & y values)");
                    myArena = new RobotArena(input.nextInt(), input.nextInt());
                    doDisplay();
                    break;
                //File handling menu
                case 'F':
                case 'f':
                    System.out.println("Enter (S)ave to file, (L)oad from file, "
                            + "or (B)ack");
                    switch (input.next().charAt(0)) {
                        //Save to new file block
                        case 'S':
                        case 's':
                            if (tf.createFile()) { //If file creation successful
                                //Write arena & Robot data to chosen file
                                tf.writeAllFile(myArena.toRawString());
                                tf.closeWriteFile(); //Close file
                            } else {
                                System.out.println("No file selected");
                            }
                            break;
                        //Load from file block
                        case 'L':
                        case 'l':
                            String fileConts = "";
                            if (tf.openFile()) { //If file opened successfully
                                fileConts = tf.readAllFile(); //Read file contents into string
                                //Remove the \n from the file contents
                                fileConts = fileConts.substring(0, fileConts.length()-1);
                                loadArena(fileConts); //loads arena with data
                            } else {
                                System.out.println("No file selected");
                            }
                            break;
                    }
                    break;
                case 'x': // when X detected program ends
                    response = 'X';
                    break;
            }
        } while (response != 'X'); // test if end

        input.close(); // close the scanner
    }

    /**
     * Initialises new canvas of the current robot arena, populates the arena
     * with robots (if any exist) using showRobots in RobotArena and displays
     * the newly constructed arena on the command line.
     */
    private void doDisplay() {
        try {
            ConsoleCanvas cc = new ConsoleCanvas(myArena.getxMax(), //Re-creates canvas with arena size & student number
                                                 myArena.getyMax(), "31005504"); 
            myArena.showRobots(cc); //Shows robots on the canvas
            System.out.println(cc.toString()); //Prints the canvas to console
        } catch (ArrayIndexOutOfBoundsException e) { //catch if arena x val <8
            System.out.println("The minimum size of an arena is 8 x n");
            System.out.println("Please create an adequately sized arena before continuing");
        }
    }

    /**
     * Parses robotArena data from a loaded text file and creates a new arena from
     * the parsed information, it then parses Robot data and adds Robots to the
     * arena, if any.
     * @param fileConts The contents of the file that contain Arena and Robot data
     */
    
    private void loadArena(String fileConts){
        String [] allData = fileConts.split(";"); //Splits data by arena info, then by each robot
        String [] arenaSize = allData[0].split(" "); //Splits arena info into it's input integer values
        myArena = new RobotArena(Integer.parseInt(arenaSize[0]), //Creates new arena 
                                 Integer.parseInt(arenaSize[1])); //with integer values
        
        for (int i = 1; i < allData.length; i++) { //loop through rest of Robot data
           Robot r = new Robot(allData[i]); //Adds each new robot by calling constructor, which will further parse data
           myArena.addRobToList(r); //Add robot to robotArena'input ArrayList
        }
        System.out.println("Successfully loaded from "+ tf.usedFileName());
    }

    public static void main(String[] args) throws InterruptedException {
        RobotInterface r = new RobotInterface(); // just calls the interface
    }
}