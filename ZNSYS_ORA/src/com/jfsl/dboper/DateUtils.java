//package com.jfsl.dboper;
//
//import java.text.ParseException;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import org.apache.log4j.Logger;
//
//public class DateUtils 
//{
//	private Logger logger = Logger.getLogger(this.getClass());
//	public static SimpleDateFormat sf_all = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
//	
//	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//	public static SimpleDateFormat sf_1 = new SimpleDateFormat("hh:mm");
//	public static SimpleDateFormat sf_2 = new SimpleDateFormat("MM月dd�?);
//	public static SimpleDateFormat sf_3 = new SimpleDateFormat("yyyy�?);
//	
////	private static DateUtils dateUtils = null;
////	
////	public static DateUtils getInstance()
////	{
////		if(dateUtils==null)
////		{
////			dateUtils = new DateUtils();
////		}
////		return dateUtils
////	}
//	public Calendar stringToCalendar(String str){
//		SimpleDateFormat localTime = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar cal = Calendar.getInstance();
//		Date date1 = new Date();
//		try {
//			date1 = localTime.parse(str);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			logger.error("异常:" + e);
//		}
//		cal.setTime(date1);
//		return cal;
//	}
//	
//	/**
//	 * @方法描述：取得今日时�?
//	 * @输入：格式，包括:yyyy-MM-dd yyyyMMdd yy-MM-dd yyMMdd
//	 * @输出：时间字符串
//	 */
//	public String getTime(String templet){
//		Calendar cal = Calendar.getInstance();
//		String yyyy = String.valueOf(cal.get(Calendar.YEAR));
//		String MM = String.valueOf(cal.get(Calendar.MONTH)+1);
//		String dd = String.valueOf(cal.get(Calendar.DATE)+1);
//		if(MM.length()== 1)
//		{
//			MM = "0"+MM;
//		}
//		if(templet.equals("yyyy-MM-dd"))
//		{
//			return yyyy+"-"+MM+"-"+dd;
//		}
//		else if(templet.equals("yyyyMMdd"))
//		{
//			return yyyy+MM+dd;
//		}
//		else if(templet.equals("yy-MM-dd"))
//		{
//			return yyyy.substring(2)+"-"+MM+"-"+dd;
//		}
//		else if(templet.equals("yyMMdd"))
//		{
//			return yyyy.substring(2)+MM+dd;
//		}
//		else if(templet.equals("yyyymm"))
//		{			
//			return yyyy+MM;
//		}
//		else
//		{
//			return "";
//		}
//	}
//	
//	/**
//	 * 获取今日�?00�?00�?00时刻
//	 *
//	 */
//	public static Date getToday1(){
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		return cal.getTime();
//	}
//	
//	
//	/**
//	 * 得到当天�?
//	 * 
//	 * @return
//	 */
//	public static Date getCDayInCurrentYear() {
//		Calendar calendar = Calendar.getInstance();
//		return calendar.getTime();
//	}
//
//	//确定是否可以操作时间
//	public static boolean isBusiness(){
//		Calendar cal = Calendar.getInstance();
//		Calendar cal1 = Calendar.getInstance();
//		Calendar cal2 = Calendar.getInstance();
//		cal1.set(Calendar.HOUR_OF_DAY, 23);
//		cal1.set(Calendar.MINUTE, 30);
//		cal2.set(Calendar.HOUR_OF_DAY, 0);
//		cal2.set(Calendar.MINUTE, 30);
//		if(cal.after(cal1) || cal.before(cal2)){
//			return false;
//		}else{
//			return true;
//		}
//	}
//	
//	public static Date getYesterday(){
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -1);
//		return cal.getTime();
//	}
//	
//	/**
//	 * 获取时间信息 string[2] 格式�?
//	 * 	{yyyy-MM-dd HH:mm:ss�?13:54/10�?13�?/2006年}
//	 * 
//	 * @param d
//	 * @return
//	 */
//	public static String[] getTimeStr(Date d){
//		
//		if(d == null){
//			return new String[]{"",""};
//		}
//		String[] str = new String[2];
//		
//		//SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
//		str[0] = sf.format(d);
//		Calendar cal = Calendar.getInstance();
//		Calendar cal2 = Calendar.getInstance();
//		cal2.setTime(d);
//		if(cal2.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)){
//			if(cal2.get(Calendar.HOUR_OF_DAY) < 12){
//				str[1] = "上午"+sf_1.format(d);
//			}else{
//				str[1] = "下午"+sf_1.format(d);
//			}
//		}else if(cal2.get(Calendar.YEAR) == cal.get(Calendar.YEAR)){
//			str[1] = sf_2.format(d);
//		}else{
//			str[1] = sf_3.format(d);
//		}
//		return str;
//	}
//	
//	
//	public static String getTimeALL(Date d)
//	{
//		
//		if(d == null)
//		{
//			return "";
//		}
//		return sf_all.format(d);
//	}
//	
//}
