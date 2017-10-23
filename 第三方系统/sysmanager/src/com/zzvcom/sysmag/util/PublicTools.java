package com.zzvcom.sysmag.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class PublicTools
{
    //log.Logbase logbase = new Logbase(this);
    // 整数到字节数组的转换
    public static byte[] toByteArray(long number)
    {
        long temp = number;
        byte[] b=new byte[4];
        for (int i = b.length - 1; i > -1; i--)
        {
            //System.out.println("temp:"+Long.toBinaryString(temp));
            b[i] = (new Long(temp & 0xff)).byteValue();
            //System.out.println(b[i]);
            temp = temp >> 8;

        }
        return b;
    }
    // 字节数组到整数的转换
    public static long toInteger(byte[] b)
    {
        long s = 0;
        long tempvalue =0;
        for (int i = 0; i < b.length; i++)
        {
            if (b[i] < 0) {
                tempvalue = 256 + b[i];
            } else {
                tempvalue = b[i];
            }
            if (i == 0) {
                s = s + tempvalue * 256 * 256 * 256;
            }

            if (i == 1) {
                s = s + tempvalue * 256 * 256;
            }
            if (i == 2) {
                s = s + tempvalue * 256;
            }
            if (i == 3) {
                s = s + tempvalue;
            }
        }
        return s;
    }
    static void test()
    {
        long i = 4269364617l;
        PublicTools.toByteArray(i);
    }


    /*提取当前时间*/
   public static  String getCurtimeOfString(String dateFormat){
       String scurdate="";

       scurdate =  getDateToString(Calendar.getInstance().getTime()
           ,dateFormat);

       return scurdate;
   }


    /*提取当前时间*/
    public static   java.util.Date getCurtimeOfDate(){
       return Calendar.getInstance().getTime();
    }

    /*转换日期格式*/
    public static  String getDateToString(Date curDate,String dateFormat){
        String scurdate="";

        if (dateFormat == null || dateFormat.trim().length() == 0){
            dateFormat = "yyyyMMddHHmmss";
        }

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
       // Calendar calendar = Calendar.getInstance();
       // Date curDate = calendar.getTime();
        scurdate = format.format(curDate);

        return scurdate;
    }

      /*转换日期格式*/
       public static  Date getStringToDate(String vdates,String vdateFormat){
           String scurdate="";
           Date date = null;
           //date =java.sql.Date.valueOf(vdates);
           SimpleDateFormat sf = new SimpleDateFormat(vdateFormat);
           try{
                   date = sf.parse(vdates);
           }
           catch(ParseException e){
                   e.printStackTrace();
           }


          /* Date date = new Date();
           Calendar c =Calendar.getInstance();

           if (vtodateFormat == null || vtodateFormat.trim().length() == 0){
               vtodateFormat = "yyyy/MM/dd HH:mm:ss";
           }

           SimpleDateFormat format = new SimpleDateFormat(vtodateFormat);
          // Calendar calendar = Calendar.getInstance();
          // Date curDate = calendar.getTime();
           scurdate = format.format(vtodateFormat);*/

           return date;
    }

    /*分割字符串 返回List
     */
    public static List splitStringToList(String splitString,String splitSign){
        List splitList = new ArrayList();
        int order = 0 ;
        StringTokenizer sfg = new StringTokenizer(splitString, splitSign);
        while (sfg.hasMoreElements()) {
            splitList.add(order,sfg.nextElement());
            order++;
        }

        return splitList;
    }
    
    public static String spliceResultListColToStringWithComma(List list, String column){
    	String strs = "";
    	String col = column.toUpperCase();
		for (int i = 0; i < list.size(); i++) {
			Map map = (HashMap) list.get(i);
			String str = (String) map.get(col);
			strs += "'" + str + "',";
		}
		return strs.substring(0, strs.length() - 1);
    }

    public static String getLocalIP(){
        String ip = "";
        try{
            ip = InetAddress.getLocalHost().toString();
        }catch(UnknownHostException ue){
            ue.printStackTrace();
        }
        return ip;
    }

    /**

    * 提供精确的小数位四舍五入处理。

    * @param v 需要四舍五入的数字

    * @param scale 小数点后保留几位

    * @return 四舍五入后的结果

    */

   public static float round(float v,int scale){

       if(scale<0)
  {

       throw new IllegalArgumentException("The scale must be a positive integer or zero");

       }

       BigDecimal b = new BigDecimal(Double.toString(v));

       BigDecimal one = new BigDecimal("1");

       return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue();

   }

   public static float yuanofjiaoadd(float v){
       String value =flaottostr(v);
       //System.out.println("v:"+v);
       float floatv = round((float)(0.4 + (Float.valueOf(value)).floatValue()),0);
      // System.out.println("v:"+v+";floatv:"+floatv);
       return floatv;
   }

   public  static String   flaottostr(float   d){
                 String   str="";
                 java.text.DecimalFormat df = new  java.text.DecimalFormat("#################0.0###");//设置输出数值的格式为XX.XX
                 str=df.format(d);
                 return   str;

    }

    public static void main(String[] args)
   {
      //System.out.println(getDateToString(getCurtimeOfDate(),"yyyy-MM-dd"));
      //amount*((Float)scale.get("perscale")).floatValue()
      float obj = 0.011f;
      float a=obj*100;
      //a = round((float)(a*0.1),4);
      System.out.println(a);
      System.out.println(yuanofjiaoadd((a)));

      List list = new ArrayList();
      List list1 = new ArrayList();
      list.add("1");
      list1.add("2");
      list.addAll(list1);
      for(int i=0;i<list.size();i++){
          System.out.println(list.get(i));
      }
      //System.out.println(list.size());
   }


}

