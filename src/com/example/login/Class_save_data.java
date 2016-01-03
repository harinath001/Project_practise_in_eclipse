package com.example.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.json.Class_server_details;
import com.example.json.JSONParser;
import com.example.project_practise.Response;

public class Class_save_data {
		public static Response save_data(String username,String email,String phone,String password,String confirm_password)
		{
			
			Response res=new Response();
			res.bool=false;
			res.message="message not set";
	
			if(Class_server_details.server_on==1)
			{
				String url=Class_server_details.server_ip+"/android/project/register.php";
				JSONParser parser=new JSONParser();
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password",password));
				params.add(new BasicNameValuePair("email",email));
				params.add(new BasicNameValuePair("phone",phone));
				
				JSONObject result;
				
				
				int result_code=-1;
				try {
					result= parser.makeHttpRequest(url, "POST", params);
					
					
						result_code=result.getInt("success");
						String message=result.getString("message");
						res.message=message;	
					
						
				} catch (JSONException e) {
					res.message="json exception occured";
					result_code=0;
				}
				catch(NullPointerException e)
				{
					res.message="null pointer exception";
					result_code=0;
				}
				catch(Exception e)
				{
					res.message="unknown exception occured";
					result_code=0;
				}
				if(result_code==1)
				{
					res.bool=true;
				}
				else
				{
					res.bool=false;
				}
			}
			else if(Class_server_details.server_on==0)
			{
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					
				}
				
				int random=(int)(Math.random()*100);
				if(random>80)
				{
					res.message="without server - success";
					res.bool=true;
				}
				else
				{
					res.message="without server - failed";
					res.bool=false;
				}
			}
			return res;
		}
}
