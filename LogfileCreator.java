import java.io.*;
import java.util.*;

/**
 * A class for creating log files of random data.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version    2016.02.29
 */
public class LogfileCreator
{
    private Random rand;

    /**
     * Create log files.
     */
    public LogfileCreator()
    {
        rand = new Random();
    }
    
    /**
     * Create a file of random log entries.
     * @param filename The file to write.
     * @param numEntries How many entries.
     * @return true if successful, false otherwise.
     */
    public boolean createFile(String filename, int numEntries)
    {
        boolean success = false;
        
        if(numEntries > 0) {
            try (FileWriter writer = new FileWriter(filename)) {
                LogEntry[] entries = new LogEntry[numEntries];
                //we are going to create entries for each year and month
                for (int year = 2015; year <= 2019;year++){
                    for (int month = 1; month <= 12; month++){
                      for (int i = 0; i < numEntries / 60; i ++){//Assumes that there are 60 entries per month
                          //Calculates the index in the array for current entry
                          int index = (year - 2015) * 12 * numEntries / 60 + i;
                          //Create a log entry for current year and ,month.
                          entries[index] = createEntry(year,month);
                      }
                    }
                }
                
                Arrays.sort(entries);
                for(int i = 0; i < numEntries; i++) {
                    writer.write(entries[i].toString());
                    writer.write('\n');
                }
                
                success = true;
            }
            catch(IOException e) { 
                System.err.println("There was a problem writing to " + filename);
            }
                
        }
        return success;
    }
    
    /**
     * Create a single (random) entry for a log file.
     * @return A log entry containing random data.
     */
    public LogEntry createEntry(int year, int month)
    {
        //int year = 2016;
        //int month = 1 + rand.nextInt(12);
        // Avoid the complexities of days-per-month.
        int day = 1 + rand.nextInt(28);
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);
        return new LogEntry(year, month, day, hour, minute);
    }

}
