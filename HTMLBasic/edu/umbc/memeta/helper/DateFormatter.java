/*
 * Created on May 12, 2005
 *
 */

package edu.umbc.memeta.helper;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Sandeep Balijepalli
 *
 */

public class DateFormatter {

	public static void main(String[] args) {
		System.out.println(getFormattedDateTime("Fri, 13 May 2005 12:28:44 GMT", "10"));
	}
	
	public static String getFormattedDateTime(String time, String when){
		String formatted = "0000-00-00 00:00:00";
		
		 //time = "Fri, 13 May 2005 01:28:44 GMT";
		 //when = "0";
		 
		 DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT ,Locale.UK);
		 TimeZone tz = TimeZone.getTimeZone("GMT");
		 // Format the current time.
		 df.setTimeZone(tz);
		 SimpleDateFormat formatter = 
		 	new SimpleDateFormat ("EEE, dd MMM yyyy HH:mm:ss z");
		 formatter.setTimeZone(tz);

		 // Parse the previous string back into a Date.
		 ParsePosition pos = new ParsePosition(0);
		 Date date = formatter.parse(time, pos);
		 Calendar c = Calendar.getInstance(tz); //df.getCalendar();
		 c.setTimeZone(tz);
		 c.setTime(date);
		 c.add(Calendar.SECOND, Integer.parseInt(when));
		 //System.out.println(c.toString());
		 String format="yyyy-MM-dd HH:mm:ss";
		 // [After]String format="yyyy-MM-dd HH:mm:ss";
		 java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(format, Locale.UK); 
		 f.setTimeZone(tz);
		 formatted = f.format(c.getTime()); 
		 //System.out.println(formatted); 		 
		 return formatted;
	}
}
