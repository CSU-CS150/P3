import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * <h1> Practical 3 : Traveling Alchemist the Almost MUD </h1>
 *
 *  In this assignment, we focus heavily on string and text manipulation. This is based on the old
 *  <a href="https://en.wikipedia.org/wiki/MUD">MUDs</a>
 *  or <u>M</u>uli - <u>U</u>ser <u>D</u>ungeons, which are some of the first examples of
 *  virtual worlds <a href="https://en.wikipedia.org/wiki/Online_text-based_role-playing_game">online</a>.
 *  This example also includes GIS positioning data (latitude and longitude)
 *  and a formula used to calculate distances in GIS applications. As such, through the game
 *  we are learning about GIS and also some of the first MMORPGs around.
 *
 *  <h2>What You'll Learn</h2>
 *  <ul>
 *      <li>String functions</li>
 *      <li>Client input</li>
 *      <li>String concatenation</li>
 *      <li>Program flow control</li>
 *      <li>Complex boolean logic</li>
 *  </ul>
 *
 * <h2>How to win?</h2>
 * This "game" is very limited due to the scope of the assignment. You are only writing the travelling function to it,
 * but a fun way to challenge yourself will be to see if you can find a path to visit all the cities in the
 * least amount of time possible. This is a well known problem in computer science, that we will talk about in class.
 *
 * Why are you visiting the cities? To tell them your love potion #9 of course! or whatever reason you can think about.
 * MUDs were often very open in their feel depending on how people played them.
 *
 * @author YOUR NAME <br>
 *         YOUR EMAIL <br>
 *         Computer Science Department <br>
 *         Colorado State University
 * @version 1.0
 */
public class Practical3 {

    // do not modify these constant variables - you will see them used throughout the code
    public static final String DEFAULT_SAVE_FILE = "savedData.txt";
    public static final int MAX_MILES_PER_DAY = 20;
    public static final int EARTH_RADIUS  = 3961;  //radius of the earth at 39 degrees latitude in miles - to use Kilometers: 6373
    public static final char CITY_DELIMINATOR = ';'; // used to split data into cities
    public static final char CITY_DATA_DELMINATOR = ',';  // used to split city data into Name,Latitude,Longitude
    public static final char DEGREE = '°';
    public static final char MINUTE = '\'';
    public static final char SECOND = '"';
    public static final String DEFAULT_STARTING_CITY = "Fort Collins,40°35'6.9288\"N,105°5'3.9084\"W";
    public static final String PREVIOUS_DELIMINATOR = ":";

    // both cities and inventory will be set in the load function which either defaults or loads a file.
    // you will read these variables but you will not modify them directly.
    String cities = "";
    String inventory = "";
    Scanner playersChoice = new Scanner(System.in);

    // you will modify these two variables to keep track of data
    int totalDaysTraveling = 0;
    String breadcrumbs = DEFAULT_STARTING_CITY; // start at the default city
    String previousPath = "";


    /**
     *  The main menu calls the {@link #printMainMenuOptions()} method to first print out the menu. It then
     *  gets the players choice by calling the {@link #getPlayersChoice()}. In this method, you will have
     *  free form answers from the player, they can type most anything. However, if what they type meets
     *  the following conditions, you will respond accordingly.
     *  <ul>
     *      <li>travel, goto <br>
     *          Gives control to the travel portion of the game by calling the {@link #travelMenu()} method. They
     *          may type either travel or goto
     *      </li>
     *      <li>stats, print <br>
     *          Calls the pre-made {@link #printStats()} method. This will also check to see if the end
     *          condition of the game has been met. This has been pre-done, but you will want to look them over.
     *          They may error if the variables are being not set in the methods you implement.
     *      </li>
     *      <li>exit, quit, bye <br>
     *          Exit the program by calling return.
     *      </li>
     *      <li>DEFAULT (anything else) <br>
     *          You will call the {@link #printInvalidOption(String)} method passing in "Exit, Goodbye, Travel".
     *      </li>
     *  </ul>
     *
     * <P>
     *  <b>Easter Eggs? </b>
     *  Open ended inputs such as these were/are common ways to add easter eggs
     *  such as asking "What is Answer to the Ultimate Question of Life, the Universe, and Everything?" and the
     *  computer returning 42, or you can also ask "What is best in life?" and have Conan's famous answer.
     *  In this assignment if a player types bored or dice or roll, you will start the dice game, by calling the
     *  {@link #diceRollerGame()}.
     *  </p>
     *  <p>
     *  Curious about easter eggs, and you own a google home or android phone? Got to here:
     *  <a href="https://www.the-ambient.com/features/best-google-home-easter-eggs-166">https://www.the-ambient.com/features/best-google-home-easter-eggs-166</a>
     * </p>
     */
    public void mainMenu() {
        printMainMenuOptions();
        String choice = getPlayersChoice().toLowerCase();

        //todo

        //endtodo

        mainMenu();
    }


    /**
     * This menu contains the main 'action' for the game. And that action is to travel to different cities.
     * Like a traditional MUD, there isn't actually a menu of actions to perform (like we saw in ASCII Artist).
     * Instead, it simply asks for a destination. This doesn't mean we don't want more actions, but it means
     * we must instead look at the clients input and figure out what action they really want to do. If they
     * type something that isn't an option, we display a help message. String contains method will be used for most of
     * check.
     *
     * The first that will happen, is that you will call the {@link #printTravelMenu()} method to print out
     * the travel menu description. We have this as a separate menu to minimize typos, and this can also allow us
     * to do things such as print it graphically.
     *
     * Here are the following actions you must support / implement - at a minimum -.
     *
     * <ul>
     *  <li>
     *      City Name <br>
     *      the name of the city. Someone should be able to write Fort, fort collins or Fort Collins. This is true
     *      for all multiple name cities. The simplest solution may be to just search for "fort" in fort collins case. Please
     *      note cities should not be hard coded. Instead, you must search for the city in the String cities that will be
     *      loaded in from a file.  If someone types in the name of the city, you will have them travel to the city calculating
     *      the time traveled. see {@link #travel(String)}
     * </li>
     * <li>
     *      exit, quit, return, cancel <br>
     *      if the player types any of the above terms, you should return to the see {@link #mainMenu()}
     * </li>
     * <li>
     *     list, ls <br>
     *     if the player types either list or ls, then you should list all the cities just as city names. You will do
     *     this by calling the {@link #listCities(String)} method.
     * </li>
     * <li>
     *     DEFAULT case (anything else)<br>
     *     if the player types anything else (including ?, help, wth) then you will call the {@link #printInvalidOption(String)} method
     *     passing in the examples of "Name of city (ex: Fort Collins), list cities, quit, return"
     * </li>
     * </ul>
     *
     * <p>Some useful String methods for this method are:</p>
     * <ul>
     *     <li>toLowerCase()</li>
     *     <li>contains</li>
     * </ul>
     *
     */
    public void travelMenu() {
        //todo

        //endtodo

        System.out.println();
        System.out.println();
        travelMenu();
    }

    /**
     * This method lists the cities that are in the cityList provided. The reason why we provide a city list is so
     * it can be used to list the cities both in breadcrumbs and in cities or any other variable we set that
     * follows the same citylist format. It should only print the city name. For example:
     * <pre>
     *     Fort Collins,40°35'6.9288"N,105°5'3.9084"W;Denver,39°44'31.3548"N,104°59'29.5116"W
     *
     *     would print
     *
     *     Fort Collins
     *     Denver
     *
     * </pre>
     *
     * This method will integrate loops and string methods. You will use both the CITY_DATA_DELIMINATOR and the
     * CITY_DELMINATOR constants
     *
     * @param cityList A list of cities in the standard cityList format (full city data)
     */
    public void listCities(String cityList) {
        //todo

        //endtodo
    }


    /**
     * This method is the heart of the what you will be implementing for Practical 3. It also has some of the hardest
     * logic, so we recommend writing it down on paper to figure it out. Some useful String methods to use are
     * <ul>
     *     <li>lastIndexOf()</li>
     *     <li>indexOf()</li>
     *     <li>length()</li>
     * </ul>
     * This method can be broken up into parts. However, since multiple things need to be tracked, it makes sense to
     * still keep it all in one method. (Learning how to write your own objects will save you this in the future)
     *
     * The flow of the method is as follows
     *
     * <ol>
     *     <li>Call {@link #getCurrentCity()}  to set the current city</li>
     *     <li>Looking at your typical city string which consists of the following formatting :
     *          <pre>
     *              Fort Collins,40°35'6.9288"N,105°5'3.9084"W
     *
     *              or
     *
     *              CityName,Latitude,Longitude
     *          </pre>
     *          you will want to break it up into three variables - the name, the latitude (as a String), the longitude (as a String)
     *     </li>
     *     <li>
     *         You will then call the {@link #convertDegreesToDecimals(String)} method to convert
     *         the latitude and longitude to doubles each respectively
     *     </li>
     *     <li>
     *         Using the destination parameter, you will call {@link #getCityInfo(String)} method, so you can get the substring from cities
     *         that contains the one city you want to look at. It will be in the exact same format as above
     *     </li>
     *     <li>
     *         Using the "next" city info, you will follow a similar process above - creating a double values
     *         for the next Cities latitude and longitude. This will give you the decimal (double) values for
     *         both the starting city and the destination city.
     *     </li>
     *     <li>
     *         Using the four values, you will specifically call the {@link #haversine(double, double, double, double)}  method
     *         This method is an advanced GIS formula used to calculate distances with the knowledge the earth
     *         is round. It is best used for short distances. If you are interested in learning more,
     *         view the javadoc for the haversine method. Since advanced trig is not required for this class,
     *         we have programmed it for you.
     *
     *         An example from our code calling the method is as follows - naturally your variable names may differ.
     *         <pre>
     *             double distance = haversine(currentLat, currentLog, nextLat, nextLog);
     *         </pre>
     *     </li>
     *     <li>
     *         After getting the distance (in miles due to the setting above) You will calculate the
     *         number of days it takes to travel to the destination. Please note, that it will take
     *         at least oce day to travel anywhere (the traveler has to visit their locations after all).
     *         You will want to round the distance variable using Math.round()
     *     </li>
     *     <li>
     *         You will then want to increment the totalDaysTraveling variable with the number of days variable.
     *         You will also want to add the destination city to the 'breadcrumbs' variable. (HINT: make sure to add
     *         the CITY_DELIMINATOR before you add the next destination to it)
     *         For example, if you started in fort collins, and you traveled to Denver, breadcrumbs should look like
     *         the following
     *         <pre>
     *             Fort Collins,40°35'6.9288"N,105°5'3.9084"W;Denver,39°44'31.3548"N,104°59'29.5116"W
     *         </pre>
     *     </li>
     *     <li>
     *         You will then call the {@link #printTravelRecord(String, String, int)} method
     *     </li>
     *
     * </ol>
     * A couple hints for this method:
     *  Use the CITY_DELIMINATOR, CITY_DATA_DELMINATOR, and the MAX_MILES_PER_DAY constants. They are there for a
     *  reason, and you should not be hard coding the deliminators at all.
     *
     *  <p>
     *  Take it one step at a time, and consider using System.out.println() to print out variables as you work with them
     *  This is how your instructor built the method. At no point, did he just write it all and see if it ran.
     * </p>
     * @param destination they city they wish to travel to
     */
    public void travel(String destination) {
       //todo

        //endtodo

    }


    /**
     * This method gets the current city from the breadcrumbs variable. You will want to use the CITY_DELIMINATOR
     * but remember, that if the lastIndexOf something is not int he string, -1 is returned. Examples
     * <pre>
     *     if breadcrumbs equals Fort Collins,40°35'6.9288"N,105°5'3.9084"W
     *     then Fort Collins,40°35'6.9288"N,105°5'3.9084"W will be returned
     *
     *     if breadcrumbs equals C;Denver,39°44'31.3548"N,104°59'29.5116"W
     *     then Denver,39°44'31.3548"N,104°59'29.5116"W will be returned
     *
     * </pre>
     * @return the full city info in the format of CityName,latitude,longitude (EX: Denver,39°44'31.3548"N,104°59'29.5116"W)
     */
    public String getCurrentCity() {
        // first get current city
        String current;

        return current;
    }

    /**
     * Get the city info by using a substring of a city name. For example if the parameter destination equaled
     * <pre>
     *     fort
     *
     *     then
     *     Fort Collins,40°35'6.9288"N,105°5'3.9084"W
     *
     *     will be returned
     * </pre>
     *
     * You can assume the start of the city is typed, and someone isn't using 'collins' to find fort collins.
     * You will want to use the CITY_DELIMINATOR constant in this method, and the cities class variable. Useful
     * string methods to look at
     * <ul>
     *     <li>length()</li>
     *     <li>indexOf</li>
     *     <li>subString</li>
     * </ul>
     * @param destination the substring of a city name to be matched so the full city data can be pulled from the cities string. It should be lowercase.
     * @return a full city data line (EX: Fort Collins,40°35'6.9288"N,105°5'3.9084"W)
     */
    public String getCityInfo(String destination) {
        //todo
        return ""; // you will want to change this
    }

    /**
     * Convert a latitude or longitude stored in coordinate degrees, minutes, seconds, direction format to decimal (double)
     * format. For example, 40°35'6.9288"N would return 40.585258 and 105°5'3.9084"W would return -105.084419.
     * You will note that West values and North values return negative values.
     *
     * <p>
     * For simple coordinate conversion you take the degree part of the value, add the minute part divided by 60
     * and then add the second part divided by 3600. For example, 105°5'3.9084"W would be 105 + (5 / 60) + (3.9084/ 3600)
     * and then you would multiply it by -1 to make it the negative coordinate.
     * </p>
     *
     * <p>
     *     You will want to use the DEGREE, MINUTE ane SECOND constants while looking at indexOf and substring.
     *     Double.parseDouble(stringValue) will return the double of the number once you *only* have a number.
     * </p>
     *
     * <p>
     *     This is another method where your instructor put in printlns while working on it to make sure
     *     he was getting the proper substrings.
     * </p>
     *
     * @param strValue A coordinate in the format of degree°minute'second"cardinal_direction or example 105°5'3.9084"W
     * @return the decimal value of that coordinate as a double.
     */
    public double convertDegreesToDecimals(String strValue) {
        //todo
        double decimal = 0;

        /// hint you should toss in some temp print lines to makes sure you don't have an OB1 error before continuing


        return decimal;
    }


    /**
     * You should start all good games with a splash screen!
     * Feel free to have fun - after you hav completed the rest of the assignment. Make sure you print a blankline
     * after the splashscreen
     */
    public void splashScreen() {
        //TODO f
        System.out.println("Welcome to the Traveling Alchemist");
        // You should have fun here! We would love to see what you can think of doing.
        //endtodo
        System.out.println();
    }

    /**
     * It is good to also have an exit screen - have fun!
     */
    public void exitScreen() {
        System.out.println();
        // TODO you should have some fun here. WE would love to see what you are doing. Just leave
        // the blank line at the tope
        System.out.println("Until we meet again traveler");
    }



//------------------------------- you do not need to modify anything below this line but you may want to read through it


    /**
     * A quick game built into the game that has the person roll dice and compete against a computer opponent
     *
     * The easter egg is triggered by adding roll, dice, bored as options in the mainMenu.
     *
     * The winner is the set with the highest average. Here is an example interaction
     * <pre>
     *     Traveler, it appears you are bored. Why don't you roll some dice.
     *     Please enter then number of sides on your dice:   6
     *     Please enter the number of times you wish to roll your dice:  10
     *     You rolled: 4
     *     The computer rolled: 4
     *     You rolled: 5
     *     The computer rolled: 4
     *     You rolled: 6
     *     The computer rolled: 6
     *     You rolled: 2
     *     The computer rolled: 6
     *     You rolled: 4
     *     The computer rolled: 1
     *     You rolled: 4
     *     The computer rolled: 1
     *     You rolled: 2
     *     The computer rolled: 4
     *     You rolled: 6
     *     The computer rolled: 5
     *     You rolled: 5
     *     The computer rolled: 6
     *     You rolled: 1
     *     The computer rolled: 6
     *     Your average roll is: 3
     *     The computers average roll is: 4
     *     Sadly, your imaginary computer won!
     *
     * </pre>
     *
     *  This mini game, if not listed in the menu, but still comes up can be considered an easter-egg.  Easter-eggs
     *  have a long history in computer programs, especially games. Have you ever noticed the ascii art dino game in chrome?
     *
     */
    public void diceRollerGame() {
        System.out.println("Traveler, welcome to the dice roller easter egg game");
        System.out.println("It appears you are bored. Why don't you roll some dice.");
        System.out.print("Please enter then number of sides on your dice: ");
        String strSides = getPlayersChoice();
        System.out.print("Please enter the number of times you wish to roll your dice: ");
        String strTimes = getPlayersChoice();

        int sides = Integer.parseInt(strSides);
        int times = Integer.parseInt(strTimes);

        int counter = 0;
        int computerCounter = 0;
        for(int i = 0; i < times; i++) {
            int yourRoll = rollDice(sides);
            int compRoll = rollDice(sides);

            System.out.println("You rolled: " + yourRoll );
            System.out.println("The computer rolled: " + compRoll);

            counter += yourRoll;
            computerCounter += compRoll;

        }

        System.out.println("Your average roll is: " + counter / times);
        System.out.println("The computers average roll is: " + computerCounter / times);
        if( counter / times > computerCounter / times) {
            System.out.println("You won!");
        } else {
            System.out.println("Sadly, your imaginary computer won! :(");
        }


    }

    /**
     * Roles a dice based on the number of sized passed into the method, so 6 would be a six sided die and so on.
     * It will return the value to be by other methods. To generate the random portion of the roller,
     * we use (int) Math.random()*100.
     * @param numberOfSides the number of sizes for the dice
     * @return returns a random roll (number) between 1 and number of sides
     */
    public int rollDice(int numberOfSides) {
        int rand = (int)(Math.random()*100);
        return (rand % numberOfSides) + 1;
    }

    /// the following methods all deal with printing out to the screen. It would be nice if we can put them in
    /// a separate file, but that is beyond the scope of this assignment.


    /**
     * This is done in a separate method, as in practice, we can easily switch the menu to printing
     * graphically and not text based. If we put it in the main menu with the program logic, it is harder
     * to make that switch!
     */
    public void printMainMenuOptions() { // do not modify
        System.out.println();
        System.out.print(">> What would you like to do today? ");
    }

    /**
     * Prints the travel log using println. The format is
     * <pre>
     *     You start traveling from: %s to %s, and it takes %d days.
     * </pre>
     * Where the first %s matches to currentName, second %s matches to nextName and %d to days
     * @param currentName  - current city you are in (city name only)
     * @param nextName - city you are traveling to (city name only)
     * @param days - number of days it is taking
     */
    public void printTravelRecord(String currentName, String nextName, int days) {
        System.out.println(String.format("You start traveling from %s to %s, and it takes %d days.",
                currentName, nextName, days));
    }


    /**
     * Simple menu for the travel section of the game.
     */
    public void printTravelMenu() { // you do not need to modify this
        System.out.println();
        System.out.println("Get ready to travel!");
        System.out.println("-------------------");
        System.out.print(">> Destination? ");
    }

    /**
     * Used to respond to invalid commands. A better implementation of this method would actually
     * take in the invalid command, so the advice can change (for example, the games used to have
     * smart remarks when you typed in colorful metaphors)
     * @param examples
     */
    public void printInvalidOption(String examples) {   // do not modify
        System.out.println("Traveler, I don't understand what you mean. Here are some examples:  " + examples);
    }

    /**
     * This method prints the current stats which includes each city the person visited by passing breadCrumbs
     * into the {@link #listCities(String)} method, and then prints the total traveling days. This contains
     * some examples of printing, and you DO NOT need to modify this method
     */
    public void printStats() { //do not modify

        System.out.println();
        System.out.println("----- STAT Printer --");
        System.out.println("Traveler you have visited the following cities, in this order");

        listCities(breadcrumbs);
        System.out.println(String.format("The total number of days traveling are: %d", totalDaysTraveling));

        if(previousPath.contains(PREVIOUS_DELIMINATOR)) {
            int previousDistance = Integer.parseInt(previousPath.substring(0, previousPath.indexOf(PREVIOUS_DELIMINATOR)));
            System.out.println("Your previous distance was: " + previousDistance);
        }

        System.out.println();
        checkEndCondition();
    }

    /**
     * Checks to see if the traveler has completed a path starting at this start city and ending at the same
     * city they started. We don't check that the player visited every city - though an ideal game would indeed check for that.
     * We do need to check to see if they visited at least one city (so more than one city in the breadcrumbs)
     * You do not need to modify this method. We have it here as an example.
     */
    public void checkEndCondition() { // do not modify
        if(breadcrumbs.indexOf(CITY_DELIMINATOR) != breadcrumbs.lastIndexOf(CITY_DELIMINATOR)) {
            String first = breadcrumbs.substring(0, breadcrumbs.indexOf(CITY_DELIMINATOR));
            String last = breadcrumbs.substring(breadcrumbs.lastIndexOf(CITY_DELIMINATOR)+1);
            if (first.equals(last)) {
                System.out.print("Traveler you have returned home! Would you like to save your progress and start over? ");
                String answer = getPlayersChoice().toLowerCase();
                if(answer.contains(("y"))) {
                    previousPath = String.format("%d%s%s", totalDaysTraveling, PREVIOUS_DELIMINATOR, breadcrumbs);
                    breadcrumbs = DEFAULT_STARTING_CITY;
                    totalDaysTraveling = 0;
                }
            }
        }
    }


    /**
     * This is also as a separate method, so it can be reused. It would also allow further expansion by
     * putting it here.
     * @return the String value of what the player entered.
     */
    public String getPlayersChoice() {  // do not modify
       return playersChoice.nextLine();
    }



    // this is a complex math formula used in GIS system data
    // https://andrew.hedges.name/experiments/haversine/
    //dlon = lon2 - lon1
    //dlat = lat2 - lat1
    //a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
    //c = 2 * atan2( sqrt(a), sqrt(1-a) )
    //d = R * c (where R is the radius of the Earth)
    /**
     *  This is a complex math formula used in GIS system data
     *     https://andrew.hedges.name/experiments/haversine/
     *     dlon = lon2 - lon1
     *     dlat = lat2 - lat1
     *     a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
     *     c = 2 * atan2( sqrt(a), sqrt(1-a) )
     *     d = R * c (where R is the radius of the Earth)
     * @param lat1 start point latitude
     * @param lon1 start point longitude
     * @param lat2 end point latitude
     * @param lon2 end point longitude
     * @return the distance as a double
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(dlat/2), 2) +
                   Math.cos(Math.toRadians(lat1)) *
                   Math.cos(Math.toRadians(lat2)) *
                   Math.pow(Math.sin(dlon/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }


    // ******** these function deal with file input and output and saving the game data

    /**
     * Loads saved game data from a file. If the file can't be found or the format is bad, it loads the default data
     * @param fileName name of saved data file
     */
    public void load(String fileName) {
       try {
            Scanner scanner = new Scanner(new File(fileName));


            cities = scanner.nextLine();
            inventory = scanner.nextLine();
            breadcrumbs = scanner.nextLine();
            if(scanner.hasNext()) {
                previousPath = scanner.nextLine();
            }

            scanner.close();
        }catch (IOException ex) {
            System.out.println("Can't find save file, loading default data");
            cities = "Fort Collins,40°35'6.9288\"N,105°5'3.9084\"W;Denver,39°44'31.3548\"N,104°59'29.5116\"W;Boulder,40°0'53.9424\"N,105°16'13.9656\"W;Salida,38°32'4.9848\"N,105°59'56.0436\"W;Loveland,40°23'55.8852\"N,105°3'9.5148\"W;Pueblo,38°16'35.2668\"N,104°36'16.5852\"W";
            inventory = "Flashlight,1;Love Potion,9;Banana,1;Water Bottle,1;";
            breadcrumbs = "Fort Collins,40°35'6.9288\"N,105°5'3.9084\"W";
        }
    }


    /**
     * Saves the game data file, so it can reload from a save point. Not implemented
     * @param fileName file to be saved
     */
    public void save(String fileName) {
       // optional - and not graded. Only if you want to explore how to do this
    }


    /**
     * cleaner style methods are often called just before a program closes. It helps to make sure resources
     * are cleaned up before releasing control back to the computer.
     */
    public void clean() {
        playersChoice.close(); // good habit to close the stream and put anything else in here to do
    }

    /**
     * The start method of the program.
     * @param args the command line arguments. Passing in a datafile will cause it to load a saved game.
     */
    public static void main(String args[]) {
        Practical3 prac = new Practical3();

        String savedData = DEFAULT_SAVE_FILE;
        if(args.length > 0) {
            savedData = args[0]; // to allow us to give you different files
        }
        prac.load(savedData);

        prac.splashScreen();
        prac.mainMenu();
        prac.exitScreen();

        //prac.save(savedData);
        prac.clean();





    }


}
