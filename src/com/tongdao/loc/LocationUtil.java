package com.tongdao.loc;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.tongdao.http.HttpHelper;

public class LocationUtil {  
        public static String getAddress(double lat, double lng) {  
         String url = String  
                 .format("http://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&language=CN&sensor=true",  
                         lat, lng);  
         System.out.println(url);  
         String result = HttpHelper.getResult(url);  
         return jsonSax(result);  
     }  
   
     private static String jsonSax(String in) {  
         String address = null;  
         try {  
             JSONTokener tokener = new JSONTokener(in);  
             JSONObject results = (JSONObject) tokener.nextValue();  
             if (!results.getString("status").equals("OK")) {  
                 return "�޷���ȡ�����ַ��";  
             }  
             System.out.println(results.toString());  
             JSONObject result = (JSONObject) results.getJSONArray("results")  
                     .get(0);  
             address = result.getString("formatted_address");  
         } catch (JSONException e) {  
             e.printStackTrace();  
         }  
         return address;  
     }  
}  
