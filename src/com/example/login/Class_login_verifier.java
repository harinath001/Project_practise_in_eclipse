package com.example.login;

import java.util.ArrayList;
import java.util.List;



import com.example.json.Class_server_details;
import com.example.json.JSONParser;
import com.example.project_practise.Response;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

public class Class_login_verifier {

	private static String url=Class_server_details.server_ip+"/android/project/login_details.php";
	//JSONArray result;
	static int success;
	//JSONParser parser;
	static JSONParser parser = new JSONParser();
	public static Response verify(String var_username,String var_phone,String var_password) 
	{
		
		Response res=new Response();
		res.bool=false;
		res.message="message not set";
		
		if(Class_server_details.server_on==1)
		{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("username", var_username));
	        params.add(new BasicNameValuePair("phone",var_phone));
	        params.add(new BasicNameValuePair("password",var_password));
			
	        JSONObject json = parser.makeHttpRequest(url, "POST", params);
	        try {
	        	success=json.getInt("success");
	        	if(success==1)
	        	{
	        		res.bool=true;
	        	
	        	}
	        	else
	        	{
	        		res.bool=false;
	        	}
	        		
	        	res.message=json.getString("message");
			} catch (JSONException e1) {
				res.bool=false;
				res.message="JOSN exception occured";
			}
	        catch(NullPointerException e)
	        {
	        	res.message="null pointer exception occured";
	        }
	        catch(Exception e)
	        {
	        	res.message="unknown exception occured";
	        }
		}
		else if(Class_server_details.server_on==0)
		{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
			res.message="login without server";
			int var_rand=(int)(Math.random()*100);
			res.bool=false;
			if(var_rand>80)
			{
				res.bool=true;
				
			}
		}
		
		return res;
	}
	
	
}
