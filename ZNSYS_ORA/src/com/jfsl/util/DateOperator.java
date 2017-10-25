package com.jfsl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateOperator {
	
	public static Date getLastDayOfMonth(int year,int month)
	{
		Calendar c=Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(""+year+"-"+month+"-01");
			c.setTime(date);
			c.add(Calendar.MONTH,1);
			c.add(Calendar.DAY_OF_MONTH,-1);
		} catch (ParseException e) {
		}
		return c.getTime();
	}
	
	public static Date getLastDayOfMonth(String year,String month)
	{
		Calendar c=Calendar.getInstance();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(year+"-"+month+"-01");
			c.setTime(date);
			c.add(Calendar.MONTH,1);
			c.add(Calendar.DAY_OF_MONTH,-1);
		} catch (ParseException e) {
		}
		return c.getTime();
	}
}
