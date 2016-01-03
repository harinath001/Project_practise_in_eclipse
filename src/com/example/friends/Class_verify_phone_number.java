package com.example.friends;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.json.Class_server_details;
import com.example.json.JSONParser;



public class Class_verify_phone_number {
	
	static JSONParser parser = new JSONParser();
	
	
	public static boolean verification(String phone)
	{
		boolean result=false;
		
		if(Class_server_details.server_on==1)
		{
			String url=Class_server_details.server_ip+"/android/project/verify_phone.php";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", phone));
			JSONObject json = parser.makeHttpRequest(url, "POST", params);
			try {
	        	int success=json.getInt("success");
	        	if(success==1)
	        	{
	        		result=true;
	        	
	        	}
	        	else
	        	{
	        		result=false;
	           	}
	        		
	        	
			} catch (JSONException e1) {
				result=false;
			}
	        catch(NullPointerException e)
	        {
	        	result=false;
	        }
	        catch(Exception e)
	        {
	        	result=false;
	        }
		}
		else if(Class_server_details.server_on==0)
		{
			result=true;
		}
		
		return result;
	}
}
