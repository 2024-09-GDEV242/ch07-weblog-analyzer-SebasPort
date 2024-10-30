import java.util.List;
import java.util.ArrayList;

/**
 * Read web server data and analyse hourly access patterns. This project is  Log Analyzer, and it analyzes logs recorded in array
 * lists. I've created different methods such as bussiest day, hour, month, as well as quietest day, hour and month. Most of them contain
 * a iterator that goes through the array list that contains the data for the respective day/time/hour to find it's respective 
 * assignment. There are also methods such as AverageAccessPerMonth, which calculates the average access per month, like it's respective 
 * name states. I've also added a number of accesses method which gives you the number of accesses.
 * 
 * @author Sebastian Portillo
 * @version    10.28.24
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    private int [] dayCounts;
    private List <LogEntry> logEntries;
    /**
     * Create an object to analyze hourly web accesses.
     * 
     * @param fileName is the name of the log file that needs
     * to be analyzed.
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourlyaccess counts.
        //Create the array object to hold the dailyaccess counts.
        dayCounts = new int [24];
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
        logEntries = new ArrayList<>();
        
        //In the code above, I'm pretty much telling the logfile reader
        //to use the file name provided as an argument, therefore allowing me
        // to analyze different log files by file name.
    }
    /**
     * Returns the number of accesses
     * This method pretty much iterates through the "hourCounts" array
     * (which stores the number of accesses for each hour of the day)
     * by adding up the number of accesses for each hour and returns the
     * total.
     * 
     * @return the total once it's done processing.
     */
    public int numberOfAccesses() {
        int total =0;  //hold the number of accesses
        //This for each loop iterates through each hour and counts the number of accessses for each hour.
        for(int hour = 0; hour < hourCounts.length; hour ++){
            total += hourCounts[hour];
        }
        return total;
    }
    /**
     * Returns the hour of the day that had the most accesses.
     * This method also iterates through the hourCounts array and updates the one with the maximum value by comparing it to the
     * current/previus maximum 
     * 
     * @return the maxHour once it's done proccessing.
     */
    public int busiestHour(){
        int maxHour = 0; ///holds value of the hour with maximum in a day
        //The if statement inside the for each loop below compares the values of the hourCount
        //of the particular hour to the hour with maximum count
        for (int hour = 1; hour <hourCounts.length; hour++){
        if(hourCounts[hour] > hourCounts[maxHour]){
        maxHour = hour;
        }
        }
        return maxHour;
    }
    /**
     * Returns the hour of the day that had the most accesses
     * Like the busiestHour method, this method also iterates through
     * the hourCounts aray and updates the current "quietest" in this case
     * 
     * @return the quietest hour once it finishes processing.
     */
    public int quietestHour(){
        int quietest = hourCounts[0]; //hold value of the hour with minimum in a day
        //The if statement inside the for each loop (which states that the
        //current hour has less than the previous one and is not 0) compares the values
        // of the particular hour to find the quietest one and it updates it.
        for ( int i = 0; i < hourCounts.length; i ++) {
        if (hourCounts[i] > 0 && hourCounts [i] > quietest)
        quietest = hourCounts [i];
        }
        return quietest;
    }
    /**
     * Return the two busiest hours that had the most accesses 
     * Iterates throught the hourCounts to find the current two busiest
     * hours
     * @return the first busiest hour
     */
    public int busiestTwoHour (){
        int busiesthOne = 0; //holds the value for the first busiest hour
        int busiesthTwo = 0; //holds the value for teh second busiest hour
        //if the current hour is busier than the first busiest hour
        for ( int n:hourCounts){
            if(busiesthOne < n){        //the second busiest hour becomes the first busiest hour
            busiesthTwo =  busiesthOne; //the current hour becomes the first busiest hour
            busiesthOne = n;
            }
            else if (busiesthTwo < n){  //if the current hour is busier than the 2nd busiest hour but not the first..
                busiesthTwo = n;        //then it becomes the second busiest hours
            }
        }
        return busiesthOne; 
    }
    /**
     * Returns the quietest day, which is the one with the fewest number of accesses.
     * 
     *@return the quietest day.
     */
    public int quietestDay(){
        int quietestDay = 0; //hold value of quietest day
        int minAccesses = Integer.MAX_VALUE;  //holds value of the current minimum number of accesses found so far in a day
        //this for each loop iterates through the dayCounts array 
        for (int i = 0; i < dayCounts.length; i++){
        if (dayCounts[i] < minAccesses){        // checks if the current day has less accesses than the current/previous minimum
          minAccesses = dayCounts[i];           //Updates the minimum number of accesses per day
          quietestDay = i;
        }
       }
       return quietestDay;
    }
    /**
     *Returns the busiest day, which is the one with the most number of accesses.
     *
     *@return the busiest day
     */
    public int busiestDay(){
        int busiestDay = 0; //hold value of busiest day
        int maxAccesses = 0; //holds value of the current maximum number of accesses found so far in a day.
        //this for each loop interates through the dayCounts array
        for (int i = 0; i < dayCounts.length; i++){
        if (dayCounts[i] > maxAccesses){       //cheks if the current day has more accesses than the current/previous maximum
        maxAccesses = dayCounts[i];            //Updates the maximum number of accesses per day
        busiestDay = i;
        }
        }
        return busiestDay;
    }
    /**
     * Returns the total number of accesses for a given month
     * @param month the month to calculate the total accesses
     * @return the total number of accesses for that month
     */
    public int totalAccessesPerMonth (int month){
        int totalAccesses = 0; 
        //Iterates through each log entry
        for (LogEntry entry : logEntries) {
        if (entry.getMonth() == month) { //Checks if the entry's month matches the given month
        totalAccesses ++;
        }
        
    }
        return totalAccesses;
    }
    /**
     * Returns the quietest Month, which is the month with the less accessess
     * 
     * @returns quietest month
     */
    public int quietestMonth(){
        int quietestMonth = 1; //Initializes with the first month
        int minAccesses = Integer.MAX_VALUE; //initializes with the current highest value
        //Iterates through each month and finds the month with the minimum accesses
        for (int month = 1; month <= 12; month ++){
        int totalAccesses = totalAccessesPerMonth (month);
        if (totalAccesses < minAccesses){
        minAccesses = totalAccesses;
        quietestMonth = month;
        }
        }
        return quietestMonth;
    }
    /**
     * Returns the busiest Month, which is the month with the most accesses
     * 
     * @return busiest month
     */
    public int busiestMonth(){
        int busiestMonth = 1; //Initialize with the first month
        int maxAccesses = 0; //Initialize with zero accesses
        //Iterate through each and find the month with the maximum accesses
        for (int month = 1; month <= 12; month ++){
        int totalAccesses = totalAccessesPerMonth (month);
        if (totalAccesses > maxAccesses){
        maxAccesses = totalAccesses;
        busiestMonth = month;
        }
        }
        return busiestMonth;
    }
    /**
     * Returns the average number of accesses per month across all months in data log
     * @return the average number of accesses per class
     */
    public double averageAccessesPerMonth(){
        int totalAccesses = 0;//value of total classes
        //iteras through each month and adds up the total accesses
        for (int month = 1; month <= 12; month++) {
        totalAccesses += totalAccessesPerMonth (month);
        }
        //Calculates average accesses per month
        double averageAccesses = (double)totalAccesses / 12;
        return averageAccesses;
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
