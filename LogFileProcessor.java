/** 
 * This class will process a file and add IP addresses and usernames to
 * their respective hashmaps. It will count each occurrence of each
 * specific address and username and will hold that data in the map.
 * It will also print the maps, their size, and the total number of lines
 * in the file based on certain flags.

 * @author morganhardin
 * @version 1.0
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Spring 2023
 */

package logprocessor;

/** Import necessary libraries in order to use regex, read files,
 * and take user input. */
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;

public class LogFileProcessor
{
    // Creating HashMaps for addresses and usernames
    public static Map<String, Integer> addresses = new HashMap<String, Integer>();
    public static Map<String, Integer> usernames = new HashMap<String, Integer>();
    
    // Tracks number of lines in file
    public static int numberLines = 0;

    /** Reads file line by line and checks for regex patterns. If pattern is
     * found, it's added to its respective HashMap and its count value is updated.
     * Will return default output and either hashmap based on given flags. */
    public static void LogFileProcessor(String fileName, int argument)
    {
        try
        {
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);

            int count = 1;

            // Reads file line by line and checks for regexs
            for(String line:lines)
            {
                Pattern pattern = Pattern.compile("(\\d)*\\.(\\d)*\\.(\\d)*\\.(\\d)*");
                Pattern pattern2 = Pattern.compile("(user)\\s(\\w*[0-9\\-\\_]*)*");
                Pattern pattern3 = Pattern.compile("(\\b(?!(user))\\b)(\\S+)");
                Matcher matcher = pattern.matcher(line);
                Matcher matcher2 = pattern2.matcher(line);
                boolean matchfound = matcher.find();
                boolean matchfound2 = matcher2.find();

                // Checks if pattern was found
                if(matchfound)
                {
                    // if pattern matched is in address, count is updated, else its added to map
                    if(addresses.containsKey(matcher.group()))
                    {
                        int valueAddresses = addresses.get(matcher.group()) + 1;
                        addresses.put(matcher.group(), valueAddresses);
                    }
                    else
                    {
                        addresses.put(matcher.group(), count);
                    }
                }
                // Checks if pattern2 was found
                if(matchfound2)
                {
                    String userMatched = matcher2.group();
                    Matcher matcher3 = pattern3.matcher(userMatched);
                    boolean matchfound3 = matcher3.find();

                    // if pattern matched is in address, count is updated, else its added to map
                    if(matchfound3 && usernames.containsKey(matcher3.group()))
                    {
                        int valueUsers = usernames.get(matcher3.group()) + 1;
                        usernames.put(matcher3.group(), valueUsers);
                    }
                    else if (matchfound3)
                    {
                        usernames.put(matcher3.group(), count);
                    }
                }
                numberLines++;
            }
            
            // Returns output based on given flag
            if(argument == 0)
            {
                defaultOutput();
            }
            else if(argument == 1)
            {
                iterateIP();
                defaultOutput();
            }
            else if(argument == 2)
            {
                iterateUser();
                defaultOutput();
            }
            else
            {
                defaultOutput();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    // Returns size of IP address HashMap
    public static int ipSize()
    {
        int addressSize = addresses.size();
        return addressSize;
    }
    // Returns size of username HashMap
    public static int userSize()
    {
        int usernameSize = usernames.size();
        return usernameSize;
    }
    // Iterates through IP address HashMap and prints each key and value
    public static void iterateIP()
    {
        for(Map.Entry<String, Integer> address : addresses.entrySet())
        {
            System.out.println(address.getKey() + ": " + address.getValue());
        }
    }
    // Iterates through username HashMap and prints each key and value
    public static void iterateUser()
    {
        for(Map.Entry<String, Integer> user : usernames.entrySet())
        {
            System.out.println(user.getKey() + ": " + user.getValue());
        }
    }
    // Returns total number of lines and size of both HashMaps
    public static void defaultOutput()
    {
        System.out.println("\n" + numberLines + " in the log file were parsed.");
        System.out.println("There are " + ipSize() + " unique addresses in the log.");
        System.out.println("There are " + userSize() + " unique users in the log.");
    }

    // Main method to get user input on file name and flag, printing the results
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter File Name: ");
        String file = input.nextLine();
        System.out.print("Enter Argument: ");
        int argument = input.nextInt();
        System.out.println("");
        LogFileProcessor(file, argument);
    } // end main
} // end class