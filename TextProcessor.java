/** 
 * This class will process a file and use inputted regex to find
 * patterns from an inputted file. It will return the number of 
 * lines, each found pattern, and count and return the number
 * of occurences found in the file.
 *
 * @author morganhardin
 * @version 1.0
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Spring 2023
 */

package textprocessor;

/** Import necessary libraries in order to use regex, read files,
 * and take user input. */
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TextProcessor
{
    /** Main method takes inputted file and regex pattern from the user
     * and reads it line by line, finding every occurence of that pattern 
     * in the file, printing each pattern and the number of occurrences. */
    public static void main(String[] args)
    {
        try
        {
            // Takes user inputted file and regex
            Scanner input = new Scanner(System.in);
            System.out.print("Enter File Name: ");
            String fileName = input.nextLine();
            System.out.print("Enter Regex: ");
            String regex = input.nextLine();
            
            // Keeps track of line numbers and number of occurrences
            int found = 0;
            int lineNumbers = 0;

            // Creates path to read file line by line
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            
            // Regex Patterns Used: 
            // Finding A, AN, and THE: (\\s|[,';:\\.\\-\\\"])(The|the|A|a|An|an)(\\s|[,';:\\.\\-\\\"])
            // Finding Mina Harker and Mrs. Harker: ((Mina|Mrs.)\\sHarker)
            // Finding Phrases with Transylvania: ((.*)|(\\s\\w*))(Transylvania)((.*)|(\\s\\w*))(\\.|;|\\s)
            // Finding Infinitive Phrases: (to\\s\\w+)
            // Finding 'ing' words not Godalming / Helsing: (\\b(?!Godalming|Helsing)\\b\\S+)(\\w*(ing))

            /** Checks each line for regex pattern and prints the pattern 
             * when found while tracking line numbers and occurrences. */
            for(String line:lines)
            {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                boolean matchfound = matcher.find();
                if (matchfound)
                {
                    System.out.println(matcher.group());
                    found++;
                }
                lineNumbers++;
            }

            // Prints file name, number of lines, and number of occurrences
            System.out.println("\n" + fileName);
            System.out.println("Number of Lines: " + lineNumbers);
            System.out.println("Found from Regex: " + found + "\n");
            input.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}