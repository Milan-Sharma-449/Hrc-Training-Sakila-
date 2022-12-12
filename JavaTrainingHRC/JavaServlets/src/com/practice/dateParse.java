package com.practice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class dateParse {

    private static java.sql.Date getDate(String element, String frmt) throws ParseException {
        try {
            DateFormat format = new SimpleDateFormat(frmt);
            Date date = format.parse(element);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static void main(String[] args) throws ParseException {
    	
        // Initialising the Dates
        String s1 = "2020-06-06";
    	String s2 = "20200606";
    	String s3 = "2020/06/06"; 
        String s4 = "2020-06-06 00:00:00";

        // Converting the Dates
        ArrayList<java.sql.Date> arrDates = new ArrayList<>();
        arrDates.add(getDate(s1, "yyyy-MM-dd"));
        arrDates.add(getDate(s2, "yyyyMMdd"));
        arrDates.add(getDate(s3, "yyyy/MM/dd"));
        arrDates.add(getDate(s4, "yyyy-MM-dd HH:mm:ss"));
        
        // Checking their DTypes
        for(java.sql.Date i : arrDates) {
            System.out.println("Date: " + i + ", Type: " + i.getClass().getName());
        }
    }
}
