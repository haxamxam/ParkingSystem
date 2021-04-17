
/**
 * The IO class uses a scanner object to take inputs from user
 * It also prints output to user
 *
 * @author Hamza Suhail
 * @version 11.0.2, Date 11/04/2021, Student id: 102666611
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class IO

{
   
   static Scanner scan ;                     // instance of a scanner
   
   /** Takes input from user through scanner
    * it assigns string to none if input doesn't have value
    * 
    * @param  text, a string parameter
    * @return   receivedText, scanned text if exists or "none" text
    */
   
   public static String getText(String text)
   {
        // print the text string
        System.out.println(text);
        scan = new Scanner(System.in);
        // scan new line text from input
        String receivedText = scan.nextLine().trim();
        // if no input then "none"
        if(receivedText.equals(""))
        {
            receivedText = "none";
        }
        // return text
        return receivedText;
   }
   
   /**
    * 
    * Prints output to user
    * 
    * @param  text, a string parameter to be printed as output
    */
   
   public static void println(String text)
   {
       System.out.println(text);
   }
}

