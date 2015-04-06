package com.tongdao.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpHelper {
	public static String surl = "http://123.56.128.198/";
	
	public static String getUrlWithGet(String sname, Map<String, String> kv) {
		String strUrl = null;

		strUrl = surl + sname + "?";

		if (kv.size() > 0) {
			Set<String> keys = kv.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String k = (String) it.next();
				String v = kv.get(k);
				try {
					strUrl += k + "=" + URLEncoder.encode(v, "utf-8") + "&";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}

			strUrl = strUrl.substring(0, strUrl.length() - 1);
		}
		return strUrl;
	}
	
	public static String getResultWithUser(String sname, Map<String,String> kv) {
		
		String strUrl = null;
		
		strUrl = surl + sname + "?";
		
		if (kv.size() > 0) {
			Set<String> keys = kv.keySet();
			for (Iterator<String> it = keys.iterator();it.hasNext();) {
				String k = (String) it.next();
				String v = kv.get(k);
				try {
					strUrl += k + "=" + URLEncoder.encode(v, "utf-8") + "&";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}
			
			strUrl = strUrl.substring(0, strUrl.length()-1);
		}
	
		
		URL url = null;
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
			BufferedReader bufferReader = new BufferedReader(in);
			String result = "";
			String readLine = "";
			while ((readLine = bufferReader.readLine()) != null) {
				result += readLine;
			}
			in.close();
			urlConn.disconnect();
			
			return result;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;	
		}
	}
	
	
public static String getResult(String surl) {
		
		String strUrl = surl;
		URL url = null;
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
			BufferedReader bufferReader = new BufferedReader(in);
			String result = "";
			String readLine = "";
			while ((readLine = bufferReader.readLine()) != null) {
				result += readLine;
			}
			in.close();
			urlConn.disconnect();
			
			return result;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;	
		}
	}

}
