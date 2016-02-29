/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *
 * @author Test
 */
public class DateUtil {
    
    /*
    * Default date format
    */
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    /**
     * Returns the date as a given formated string
     */
    public static String format(LocalDate date)
    {
        if (date == null) {
            return null;
        }
        
        return DATE_FORMATTER.format(date);
    }
    
    /**
     * Converts a string into a calendar object
     */
    public static LocalDate parse(String dateString)
    {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    /**
     * Checks the string whether it is a valid date
     */
    public static boolean validString(String dateString)
    {
        return DateUtil.parse(dateString) != null;
    }
}
